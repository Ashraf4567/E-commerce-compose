package com.example.e_commerce_compose.presentation.screens.cart

sealed class CartEvents {
    data object LoadCart : CartEvents()
    data class RemoveItem(val id: String) : CartEvents()
    data class UpdateProductCount(val id: String, val count: Int) : CartEvents()
    data object BackPressed : CartEvents()
}