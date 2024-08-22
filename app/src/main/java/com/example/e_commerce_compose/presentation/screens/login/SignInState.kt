package com.example.e_commerce_compose.presentation.screens.login

data class SignInState(
    val email: String = "hdgfx5346@gmail.com",
    val password: String = "ashraf@123",
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailError: String = "",
    val passwordError: String= ""
)
