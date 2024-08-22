package com.example.e_commerce_compose.presentation.screens.productDetails

sealed class ProductsDetailsEvents {
    data object LoadProductDetails : ProductsDetailsEvents()
    data class AddToWishlist(val productId: String) : ProductsDetailsEvents()
    data class RemoveFromWishlist(val productId: String) : ProductsDetailsEvents()

}