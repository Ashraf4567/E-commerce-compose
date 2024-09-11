package com.example.e_commerce_compose.presentation.screens.wishlist

sealed class WishlistEvents {
    data object LoadWishlistProducts: WishlistEvents()
    data class RemoveFromWishlist(val productId: String): WishlistEvents()
    data class AddToCart(val productId: String): WishlistEvents()
    data class RemoveFromCart(val productId: String): WishlistEvents()
    data object ClearWishlist: WishlistEvents()
}