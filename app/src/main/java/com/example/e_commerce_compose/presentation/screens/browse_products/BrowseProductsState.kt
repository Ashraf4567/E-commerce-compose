package com.example.e_commerce_compose.presentation.screens.browse_products

import com.example.e_commerce_compose.domain.model.Brand
import com.example.e_commerce_compose.domain.model.Product

data class BrowseProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyList: Boolean = false,
    val screenTitle: String = "Browse Products",
    val brands: List<Brand> = emptyList(),
    val selectedBrandId: String? = null,
    val showFilters: Boolean = false,
    val fetchBrandsError: String? = null,
    val fetchBrandsLoading: Boolean = false,
    val selectedSort: String? = null
    )