package com.example.e_commerce_compose.presentation.screens.home

sealed class HomeEvents {
    data object LoadData : HomeEvents()
}