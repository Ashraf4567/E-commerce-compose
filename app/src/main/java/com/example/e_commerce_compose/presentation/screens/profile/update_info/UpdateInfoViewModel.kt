package com.example.e_commerce_compose.presentation.screens.profile.update_info

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UpdateInfoViewModel() : ViewModel() {
    private val _state = MutableStateFlow(UpdateInfoState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<UpdateInfoEffects>()
    val effects = _effects.asSharedFlow()


    fun onEvent(event: UpdateInfoEvents){
        when(event){
            is UpdateInfoEvents.SetEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is UpdateInfoEvents.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is UpdateInfoEvents.SetPhone -> {
                _state.update {
                    it.copy(
                        phone = event.phone
                    )
                }
            }
            UpdateInfoEvents.UpdateInfoClicked -> handleUpdateInfo()
        }
    }

    private fun handleUpdateInfo() {

    }
}