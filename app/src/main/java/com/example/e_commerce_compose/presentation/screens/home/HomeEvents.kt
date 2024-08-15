package com.example.e_commerce_compose.presentation.screens.home

sealed class HomeEvents {
    class OnProductClicked(val productId: String) : HomeEvents()

    data object LoadData : HomeEvents()
}