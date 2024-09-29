package com.example.e_commerce_compose.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.local.DataStoreManager
import com.example.e_commerce_compose.data.model.address.AddNewAddressRequest
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.domain.repository.AddressRepository
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.domain.repository.OrdersRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val addressRepository: AddressRepository,
    private val orderRepository: OrdersRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()



    fun getOrders(){
        viewModelScope.launch(Dispatchers.IO) {
        val userId = dataStoreManager.getUserFromDataStore().firstOrNull()?.id
        orderRepository.getUserOrders(userId?:"").collect{result->

            when(result){
                is Resource.Error -> {
                    Log.d("ProfileViewModel", "getOrders: ${result.error.message}")
                    _state.update {
                        it.copy(
                            error = result.error.message,
                            isLoading = false
                        )
                    }
                }
                Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.ServerError -> {}
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            userOrders = result.data?: emptyMap(),
                            isLoading = false
                        )
                    }
                }
            }
        }
        }
    }

    fun handleGetCurrentAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.getAddresses().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                error = result.error.message,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                addresses = result.data?.map {addressDto->
                                    addressDto?.toDomain()?: Address()
                                }?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun handleDeleteAddress(addressId: String){
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.deleteAddress(addressId).collect{result->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message
                            )
                        }
                    }
                    Resource.Loading -> {}
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                error = result.error.message
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                addresses = result.data?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateShowAddAddressDialog(show: Boolean){
        _state.update {
            it.copy(
                showAddAddressDialog = show
            )
        }
    }
    fun handleSaveNewAddress(
        onSuccessful: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.addAddress(
                AddNewAddressRequest(
                    name = _state.value.addressToAdd.name,
                    details = state.value.addressToAdd.details,
                    phone = state.value.addressToAdd.phone,
                    city = state.value.addressToAdd.city,
                )
            ).collect {result ->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                addresses = result.data?.map {addressDto->
                                    addressDto?.toDomain()?: Address()
                                }?: emptyList(),
                                isLoading = false,
                                addressToAdd = Address()
                            )
                        }
                        onSuccessful()
                    }
                }
            }
        }
    }

    fun setAddressName(name: String){
        _state.update {
            it.copy(
                addressToAdd = it.addressToAdd.copy(name = name)
            )
        }
    }
    fun setAddressDetails(details: String){
        _state.update {
            it.copy(
                addressToAdd = it.addressToAdd.copy(details = details)
            )
        }
    }
    fun setPhoneNumber(phone: String){
        _state.update {
            it.copy(
                addressToAdd = it.addressToAdd.copy(phone = phone)
            )
        }
    }
    fun setCity(city: String){
        _state.update {
            it.copy(
                addressToAdd = it.addressToAdd.copy(city = city)
            )
        }
    }
}