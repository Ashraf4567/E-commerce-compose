package com.example.e_commerce_compose.data.model.user

data class AuthResponse(
	val message: String? = null,
	val user: User? = null,
	val token: String? = null
)

data class User(
	val role: String? = null,
	val name: String? = null,
	val email: String? = null
)

