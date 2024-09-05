package com.example.e_commerce_compose.presentation.screens.browse_products

import com.example.e_commerce_compose.domain.model.Product

data class BrowseProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyList: Boolean = false,
    val screenTitle: String = "Browse Products"
    )