package com.example.e_commerce_compose.presentation.screens.signup

sealed class SignupEffects {
    data object NavigateToHome : SignupEffects()
    data object NavigateToSignIn : SignupEffects()
    data class ShowToastMessage(val message: String) : SignupEffects()

}