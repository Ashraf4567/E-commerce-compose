package com.example.e_commerce_compose.presentation.screens.productDetails

sealed class ProductDetailsEffects {
    data class ShowToast(val message: String) : ProductDetailsEffects()
}