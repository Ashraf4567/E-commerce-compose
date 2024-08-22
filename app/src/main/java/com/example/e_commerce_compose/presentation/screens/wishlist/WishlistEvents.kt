package com.example.e_commerce_compose.presentation.screens.wishlist

sealed class WishlistEvents {
    data object LoadWishlistProducts: WishlistEvents()
}