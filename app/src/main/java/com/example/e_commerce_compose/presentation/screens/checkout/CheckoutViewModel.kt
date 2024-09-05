package com.example.e_commerce_compose.presentation.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.model.AddNewAddressRequest
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.domain.repository.AddressRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val addressRepository: AddressRepository
): ViewModel() {

    private val _state = MutableStateFlow(CheckoutState())
    val state = _state.asStateFlow()

    init {
        onEvent(CheckoutEvents.GetCurrentAddress)
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
                        selectedAddressId = event.addressId
                    )
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
                                getAddressesLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.error.message,
                                getAddressesLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                userAddresses = result.data?.map {addressDto->
                                    addressDto?.toDomain()?: Address()
                                }?: emptyList(),
                                getAddressesLoading = false,
                                selectedAddressId = result.data?.firstOrNull()?.id
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