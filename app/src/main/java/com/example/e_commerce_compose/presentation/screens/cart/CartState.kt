package com.example.e_commerce_compose.presentation.screens.cart

import com.example.e_commerce_compose.domain.model.Cart


data class CartState(
    val cart: Cart = Cart(),
    val isCartEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)
