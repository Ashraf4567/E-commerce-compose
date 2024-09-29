package com.example.e_commerce_compose.presentation.screens.profile.update_info

data class UpdateInfoState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
