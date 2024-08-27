package com.example.e_commerce_compose.presentation.screens.home

sealed class HomeEffects {
    data object NavigateToCart : HomeEffects()
}