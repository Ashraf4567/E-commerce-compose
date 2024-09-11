package com.example.e_commerce_compose.presentation.screens.checkout

sealed class CheckoutEffects {
    data class ShowToast(val message: String) : CheckoutEffects()
    data class NavigateToSuccess(val totalPrice: Double , val paymentMethod: String) : CheckoutEffects()

}