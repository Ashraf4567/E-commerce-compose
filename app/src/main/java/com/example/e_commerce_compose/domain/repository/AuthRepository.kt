package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.data.model.UserCredentials
import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.domain.model.SignInResponse
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(signInRequest: SignInRequest): Flow<Resource<SignInResponse>>

    suspend fun saveUserToDataStore(userCredentials: UserCredentials)

    suspend fun getUserFromDataStore(): Flow<UserCredentials>
}