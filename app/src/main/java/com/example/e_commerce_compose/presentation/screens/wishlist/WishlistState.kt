package com.example.e_commerce_compose.presentation.screens.wishlist

import com.example.e_commerce_compose.domain.model.Product

data class WishlistState(
    val wishlistItems: List<Product> = emptyList(),
    val isWishlistEmpty: Boolean = false,
    val isLoading: Boolean = false
)
