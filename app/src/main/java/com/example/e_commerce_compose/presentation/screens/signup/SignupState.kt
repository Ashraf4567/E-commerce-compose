package com.example.e_commerce_compose.presentation.screens.signup

data class SignupState (
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val rePassword: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val nameError: String = "",
    val emailError: String = "",
    val passwordError: String= "",
    val rePasswordError: String= "",
    val phoneError: String= "",
    val termsAndConditions: Boolean = false,

)