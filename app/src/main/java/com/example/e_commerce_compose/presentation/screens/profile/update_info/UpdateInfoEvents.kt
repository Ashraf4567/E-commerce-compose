package com.example.e_commerce_compose.presentation.screens.profile.update_info

sealed class UpdateInfoEvents {
    data class SetName(val name: String) : UpdateInfoEvents()
    data class SetEmail(val email: String) : UpdateInfoEvents()
    data class SetPhone(val phone: String) : UpdateInfoEvents()
    data object UpdateInfoClicked : UpdateInfoEvents()
}