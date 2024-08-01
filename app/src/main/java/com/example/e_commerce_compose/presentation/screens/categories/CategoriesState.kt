package com.example.e_commerce_compose.presentation.screens.categories

import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.SubCategory

data class CategoriesState(
    val categories: List<Category?>? = emptyList(),
    val selectedCategory: Category? = null,
    val categoriesLoading: Boolean = false,
    val subCategoriesLoading: Boolean = false,
    val subCategories: List<SubCategory?>? = emptyList(),
    val error: String? = null,
)
