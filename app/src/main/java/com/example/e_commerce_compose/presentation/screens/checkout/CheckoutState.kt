package com.example.e_commerce_compose.presentation.screens.checkout

import com.example.e_commerce_compose.domain.model.Address

data class CheckoutState(
    val address: Address = Address(),
    val errorMessage: String? = null,
    val mainLoading: Boolean = false,
    val selectedPaymentOption: String = paymentMethods.first(),
    val userAddresses : List<Address> = emptyList(),
    val showAddressBottomSheet: Boolean = false,
    val selectedAddress: Address? = null,
    val addNewAddressLoading: Boolean = false
)
