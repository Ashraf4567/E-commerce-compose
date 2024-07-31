package com.example.e_commerce_compose.presentation.screens.categories

import com.example.e_commerce_compose.domain.model.Category

data class CategoriesState(
    val categories: List<Category?>? = emptyList(),
    val selectedCategoryId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
