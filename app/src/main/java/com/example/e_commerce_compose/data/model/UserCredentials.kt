package com.example.e_commerce_compose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val id: String,
    val name: String,
    val email: String,
    val token: String
)
