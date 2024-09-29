package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.data.model.user.AuthResponse
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(signInRequest: SignInRequest): Flow<Resource<AuthResponse>>
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ): Flow<Resource<AuthResponse>>


}