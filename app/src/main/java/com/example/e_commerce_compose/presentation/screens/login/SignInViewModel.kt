package com.example.e_commerce_compose.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.data.model.UserCredentials
import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SignInEffects>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: SignInEvents){
        when(event){
            is SignInEvents.ForgotPassword -> {}
            is SignInEvents.SetEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is SignInEvents.SetPassword -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            SignInEvents.SignIn -> {
                if(!validateSignIn())
                    return
                handleSignIn()
            }
            SignInEvents.SignUp -> {}
        }
    }

    private fun handleSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(
                SignInRequest(
                    email = state.value.email,
                    password = state.value.password
                )
            ).collect{ result ->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.message
                            )
                        }
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {}
                    is Resource.Success -> {
                        result.data
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        result.let {myResult ->
                            authRepository.saveUserToDataStore(
                                UserCredentials(
                                    email = myResult.data?.user?.email!!,
                                    name = myResult.data.user.name!!,
                                    token = myResult.data.token!!
                                )
                            )
                        }
                        _effect.emit(SignInEffects.NavigateToHome)
                    }
                }
            }
        }
    }

    private fun validateSignIn(): Boolean{
        var isValid = true
        if(state.value.email.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    emailError = "Email cannot be empty"
                )
            }
        }
        if(state.value.password.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    passwordError = "Password cannot be empty"
                )
            }
        }
        return isValid
    }
}