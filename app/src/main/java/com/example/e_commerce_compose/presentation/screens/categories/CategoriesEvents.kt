package com.example.e_commerce_compose.presentation.screens.categories

sealed class CategoriesEvents {
    data object GetCategories : CategoriesEvents()
    data class CategoryClicked(val id: String) : CategoriesEvents()
}