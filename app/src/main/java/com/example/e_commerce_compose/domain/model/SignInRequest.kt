package com.example.e_commerce_compose.domain.model

data class SignInRequest(
	val password: String? = null,
	val email: String? = null
)

