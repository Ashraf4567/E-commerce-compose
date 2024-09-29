package com.example.e_commerce_compose.presentation.screens.profile.update_info

sealed class UpdateInfoEffects {
    data class ShowToast(val message: String) : UpdateInfoEffects()
}