package com.example.e_commerce_compose.presentation.screens.categories

sealed class CategoriesEffects {
    data class NavigateToProducts(val categoryId: String) : CategoriesEffects()
}