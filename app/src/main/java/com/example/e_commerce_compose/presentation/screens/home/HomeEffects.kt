package com.example.e_commerce_compose.presentation.screens.home

sealed class HomeEffects {
    data object NavigateToCart : HomeEffects()
    data class NavigateToBrowseProducts(val categoryId : String) : HomeEffects()
    data class NavigateToProductDetails(val productId : String) : HomeEffects()
}