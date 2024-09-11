package com.example.e_commerce_compose.presentation.screens.checkout

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.model.address.AddNewAddressRequest
import com.example.e_commerce_compose.data.model.address.ShippingAddressDto
import com.example.e_commerce_compose.data.model.address.ShippingAddressRequest
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.domain.repository.AddressRepository
import com.example.e_commerce_compose.domain.repository.OrdersRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val addressRepository: AddressRepository,
    private val ordersRepository: OrdersRepository
): ViewModel() {

    private val cartId = checkNotNull(savedStateHandle.get<String>("cartId"))
    private val _state = MutableStateFlow(CheckoutState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<CheckoutEffects>()
    val effects = _effects.asSharedFlow()


    init {
        onEvent(CheckoutEvents.GetCurrentAddress)
        Log.d("CheckoutViewModel", "cartId: $cartId")
    }

    fun onEvent(event: CheckoutEvents) {
        when (event) {
            is CheckoutEvents.SetAddressDetails -> {
                _state.update {
                    it.copy(
                        address = _state.value.address.copy(
                            details = event.address,
                        )
                    )
                }
            }
            is CheckoutEvents.SetAddressName -> {
                _state.update {
                    it.copy(
                        address = _state.value.address.copy(
                            name = event.name,
                        )
                    )
                }
            }
            is CheckoutEvents.SetCity -> {
                _state.update {
                    it.copy(
                        address = _state.value.address.copy(
                            city = event.city,
                        )
                    )
                }
            }
            is CheckoutEvents.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        address = _state.value.address.copy(
                            phone = event.phone,
                        )
                    )
                }
            }

            CheckoutEvents.SaveAddressClicked -> handleSaveNewAddress()
            is CheckoutEvents.UpdateAddressBottomSheetState -> {
                _state.update {
                    it.copy(
                        showAddressBottomSheet = event.state
                    )
                }
            }

            CheckoutEvents.GetCurrentAddress -> handleGetCurrentAddress()
            is CheckoutEvents.SetSelectedAddress -> {
                _state.update {
                    it.copy(
                        selectedAddress = event.address
                    )
                }
            }

            CheckoutEvents.ConfirmOrderClicked -> handleConfirmOrder()
        }
    }

    private fun handleConfirmOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            ordersRepository.createCashOrder(
                shippingAddressRequest = ShippingAddressRequest(
                    shippingAddress = ShippingAddressDto(
                        details = _state.value.selectedAddress?.details?:"",
                        phone = _state.value.selectedAddress?.phone?:"",
                        city = _state.value.selectedAddress?.city?:""
                    )
                ),
                cartId = cartId

            ).collect{result->
                when(result){
                    is Resource.Error -> {
                        _effects.emit(
                            CheckoutEffects.ShowToast(result.error.message?:"Something went wrong")
                        )
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                mainLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _effects.emit(
                            CheckoutEffects.ShowToast(result.error.message?:"Something went wrong")
                        )
                    }
                    is Resource.Success -> {
                        _effects.emit(
                            CheckoutEffects.NavigateToSuccess(
                                totalPrice = result.data?.totalOrderPrice?.toDouble()?:0.0,
                                paymentMethod = result.data?.paymentMethodType?:""
                            )
                        )
                    }
                }
            }
        }
    }

    private fun handleGetCurrentAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.getAddresses().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.error.message
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                mainLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.error.message,
                                mainLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                userAddresses = result.data?.map {addressDto->
                                    addressDto?.toDomain()?: Address()
                                }?: emptyList(),
                                mainLoading = false,
                                selectedAddress = result.data?.firstOrNull()?.toDomain()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleSaveNewAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.addAddress(
                AddNewAddressRequest(
                    name = state.value.address.name,
                    details = state.value.address.details,
                    phone = state.value.address.phone,
                    city = state.value.address.city,
                )
            ).collect {result ->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.error.message
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                addNewAddressLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.error.message,
                                addNewAddressLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                addNewAddressLoading = false,
                                address = Address(),
                                userAddresses = result.data?.map {addressDto->
                                    addressDto?.toDomain()?: Address()
                                }?: emptyList()
                            )
                        }
                        onEvent(CheckoutEvents.UpdateAddressBottomSheetState(false))
                    }
                }
            }
        }
    }
}