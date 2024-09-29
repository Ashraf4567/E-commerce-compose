package com.example.e_commerce_compose.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.e_commerce_compose.data.local.DataStoreManager
import com.example.e_commerce_compose.data.model.user.UserCredentials
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.presentation.screens.login.SignInEffects
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewmodel(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    private val _state = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<SignupEffects>()
    val effects = _effects.asSharedFlow()

    fun onEvent(event: SignupEvents) {
        when(event) {
            is SignupEvents.SetEmail ->{
                _state.update {
                    it.copy(email = event.email)
                }
            }
            is SignupEvents.SetName -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }
            is SignupEvents.SetPassword -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }
            is SignupEvents.SetRePassword -> {
                _state.update {
                    it.copy(rePassword = event.rePassword)
                }
            }

            is SignupEvents.SetPhone -> {
                _state.update {
                    it.copy(phone = event.phone)
                }
            }
            SignupEvents.SignUp -> {
                if(!validateSignUp())
                    return
                handleSignUp()
            }

            is SignupEvents.SetTermsAndConditions -> {
                _state.update {
                    it.copy(termsAndConditions = event.isChecked)
                }
            }

            SignupEvents.SignInClicked -> {
                viewModelScope.launch {
                    _effects.emit(SignupEffects.NavigateToSignIn)
                }
            }
        }
    }
    private fun handleSignUp() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(
                name = _state.value.name,
                email = _state.value.email,
                password = _state.value.password,
                rePassword = _state.value.rePassword,
                phone =  _state.value.phone
            ).collect {results->
                when(results){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = results.error.message,
                                isLoading = false
                            )
                        }
                        _effects.emit(SignupEffects.ShowToastMessage(results.error.message.toString()))
                    }
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.ServerError -> {
                        _state.update {
                            it.copy(
                                error = results.error.message,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        results.let {myResult ->
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
                        _effects.emit(SignupEffects.ShowToastMessage("Sign in successfully"))
                        _effects.emit(SignupEffects.NavigateToHome)
                    }
                }
            }
        }
    }

    private fun validateSignUp(): Boolean {
        var isValid = true

        // Name validation
        if (_state.value.name.isBlank()) {
            _state.update { it.copy(nameError = "Name cannot be empty") }
            isValid = false
        }else{
            _state.update { it.copy(nameError = "") }
        }

        // Email validation
        if (_state.value.email.isBlank()) {
            _state.update { it.copy(emailError = "Email cannot be empty") }
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()) {
            _state.update { it.copy(emailError = "Invalid email format") }
            isValid = false
        }else{
            _state.update { it.copy(emailError = "") }
        }

        // Password validation
        if (_state.value.password.isBlank()) {
            _state.update { it.copy(passwordError = "Password cannot be empty") }
            isValid = false
        } else if (_state.value.password.length < 6) {
            _state.update { it.copy(passwordError = "Password must be at least 6 characters") }
            isValid = false
        }else{
            _state.update { it.copy(passwordError = "") }
        }

        // RePassword validation
        if (_state.value.rePassword.isBlank()) {
            _state.update { it.copy(rePasswordError = "Please confirm your password") }
            isValid = false
        } else if (_state.value.password != _state.value.rePassword) {
            _state.update { it.copy(rePasswordError = "Passwords do not match") }
            isValid = false
        }else{
            _state.update { it.copy(rePasswordError = "") }
        }

        // Phone validation
        if (_state.value.phone.isBlank()) {
            _state.update { it.copy(phoneError = "Phone number cannot be empty") }
            isValid = false
        }else{
            _state.update { it.copy(phoneError = "") }
        }

        // Terms and Conditions validation
//        if (!_state.value.termsAndConditions) {
//            _state.update { it.copy(termsAndConditionsError = "You must accept the terms and conditions") }
//            isValid = false
//        }

        return isValid
    }




}