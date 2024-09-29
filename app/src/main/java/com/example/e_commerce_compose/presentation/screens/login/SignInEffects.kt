package com.example.e_commerce_compose.presentation.screens.login

sealed class SignInEffects {
    data object NavigateToHome : SignInEffects()
    data class ShowToastMessage(val message : String) : SignInEffects()
    data object NavigateToSignUp : SignInEffects()
    data object NavigateToForgotPassword : SignInEffects()

}