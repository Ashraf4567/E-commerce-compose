package com.example.e_commerce_compose.presentation.screens.login

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailError: String = "",
    val passwordError: String= ""
)
