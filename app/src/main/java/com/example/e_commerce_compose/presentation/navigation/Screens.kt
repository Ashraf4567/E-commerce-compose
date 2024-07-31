package com.example.e_commerce_compose.presentation.navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object Categories: Screens("categories")
    data object WishList: Screens("wishList")
    data object Profile: Screens("profile")

}