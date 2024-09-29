package com.example.e_commerce_compose.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.e_commerce_compose.data.local.DataStoreManager
import com.example.e_commerce_compose.data.model.user.UserCredentials
import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _effect = Channel<SignInEffects>()
    val effect = _effect.receiveAsFlow()

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
            SignInEvents.SignUp -> {
                viewModelScope.launch {
                    _effect.send(SignInEffects.NavigateToSignUp)
                }
            }
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
                                error = result.error.message,
                                isLoading = false
                            )
                        }
                        _effect.send(SignInEffects.ShowToastMessage(result.error.message.toString()))
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
                            val token = myResult.data?.token
                            val jwt = token?.let { JWT(it) }
                            dataStoreManager.saveUserToDataStore(
                                UserCredentials(
                                    id = jwt?.getClaim("id")?.asString()?:"",
                                    email = myResult.data?.user?.email!!,
                                    name = myResult.data.user.name!!,
                                    token = myResult.data.token!!
                                )
                            )
                        }
                        _effect.send(SignInEffects.ShowToastMessage("Sign in successfully"))
                        _effect.send(SignInEffects.NavigateToHome)
                    }
                }
            }
        }
    }

    private fun validateSignIn(): Boolean{
        var isValid = true
        if(_state.value.email.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    emailError = "Email cannot be empty"
                )
            }
        }
        // handle email format validation
        if (!_state.value.email.contains("@") && !_state.value.email.contains(".")){
            isValid = false
            _state.update {
                it.copy(
                    emailError = "Invalid email format"
                )
            }
        }
        if(_state.value.password.isBlank()) {
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