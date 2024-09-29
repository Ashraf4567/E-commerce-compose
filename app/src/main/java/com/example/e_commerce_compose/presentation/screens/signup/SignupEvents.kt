package com.example.e_commerce_compose.presentation.screens.signup

sealed class SignupEvents {
    data class SetName(val name: String) : SignupEvents()
    data class SetEmail(val email: String) : SignupEvents()
    data class SetPassword(val password: String) : SignupEvents()
    data class SetRePassword(val rePassword: String) : SignupEvents()
    data class SetPhone(val phone: String) : SignupEvents()
    data class SetTermsAndConditions(val isChecked: Boolean) : SignupEvents()
    data object SignInClicked : SignupEvents()
    data object SignUp : SignupEvents()

}