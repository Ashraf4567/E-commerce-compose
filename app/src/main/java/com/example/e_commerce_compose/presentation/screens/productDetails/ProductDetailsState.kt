package com.example.e_commerce_compose.presentation.screens.productDetails

import com.example.e_commerce_compose.domain.model.Product

data class ProductDetailsState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = "",
    val wishlistOperationLoading: Boolean = false,
    val cartOperationLoading: Boolean = false
)
