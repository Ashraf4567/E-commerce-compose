package com.example.e_commerce_compose.presentation.screens.login

sealed class SignInEffects {
    data object NavigateToHome : SignInEffects()
}