package com.example.e_commerce_compose.presentation.screens.browse_products

sealed class BrowseProductsEffects {
    data class NavigateToProductDetails(val productId: String) : BrowseProductsEffects()
}