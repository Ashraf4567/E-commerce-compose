package com.example.e_commerce_compose.presentation.screens.login

sealed class SignInEvents {
    data class SetEmail(val email: String) : SignInEvents()
    data class SetPassword(val password: String) : SignInEvents()
    data object SignIn : SignInEvents()
    data class ForgotPassword(val email: String) : SignInEvents()
    data object SignUp : SignInEvents()

}