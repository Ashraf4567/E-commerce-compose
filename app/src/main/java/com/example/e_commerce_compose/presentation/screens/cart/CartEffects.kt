package com.example.e_commerce_compose.presentation.screens.cart

sealed class CartEffects {
    data class ShowToast(val message: String) : CartEffects()
    data object NavigateToCheckout : CartEffects()
    data object NavigateBack : CartEffects()
}