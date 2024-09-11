package com.example.e_commerce_compose.presentation.screens.wishlist

import com.example.e_commerce_compose.domain.model.Product

data class WishlistState(
    val wishlistItems: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val specificProductCartOperationLoading: Pair<String, Boolean> = Pair("", false),
    val specificProductWishlistOperationLoading: Pair<String, Boolean> = Pair("", false)
)
