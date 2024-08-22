package com.example.e_commerce_compose.presentation.screens.home

import com.example.e_commerce_compose.data.model.UserCredentials
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.domain.model.Product

data class HomeUiState(
    val isLoading: Boolean = false,
    val categoriesList: List<Category?>? = emptyList(),
    val productList: List<Product?>? = emptyList(),
    val currentUserName: String = "",
)