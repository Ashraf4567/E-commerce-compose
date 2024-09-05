package com.example.e_commerce_compose.presentation.screens.browse_products

sealed class BrowseProductsEvents {
    data object FetchProducts : BrowseProductsEvents()
    data class ProductClicked(val productId: String) : BrowseProductsEvents()
}