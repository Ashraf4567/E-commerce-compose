package com.example.e_commerce_compose.presentation.screens.checkout

import com.example.e_commerce_compose.domain.model.Address

sealed class CheckoutEvents {
    data class SetAddressName(val name: String) : CheckoutEvents()
    data class SetAddressDetails(val address: String) : CheckoutEvents()
    data class SetPhoneNumber(val phone: String) : CheckoutEvents()
    data class SetCity(val city: String) : CheckoutEvents()
    data object SaveAddressClicked : CheckoutEvents()
    data class UpdateAddressBottomSheetState(val state: Boolean) : CheckoutEvents()
    data object GetCurrentAddress : CheckoutEvents()
    data class SetSelectedAddress(val address: Address) : CheckoutEvents()
    data object ConfirmOrderClicked : CheckoutEvents()
}