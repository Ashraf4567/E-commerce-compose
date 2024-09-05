package com.example.e_commerce_compose.presentation.screens.categories

import com.example.e_commerce_compose.domain.model.Category

sealed class CategoriesEvents {
    data object GetCategories : CategoriesEvents()
    data class CategoryClicked(val category: Category) : CategoriesEvents()
    data class SubCategoryClicked(val categoryId: String) : CategoriesEvents()
}