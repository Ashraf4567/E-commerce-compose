package com.example.e_commerce_compose.domain.model

data class SignInResponse(
	val message: String? = null,
	val user: User? = null,
	val token: String? = null
)

data class User(
	val role: String? = null,
	val name: String? = null,
	val email: String? = null
)

