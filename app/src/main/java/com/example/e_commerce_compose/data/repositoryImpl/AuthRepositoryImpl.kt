package com.example.e_commerce_compose.data.repositoryImpl

import android.content.Context
import androidx.datastore.dataStore
import com.example.e_commerce_compose.data.local.UserSerializer
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.data.model.user.AuthResponse
import com.example.e_commerce_compose.data.model.user.SignupRequest
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

val Context.dataStore by dataStore("user_data.json" , UserSerializer)

class AuthRepositoryImpl(
    private val webServices: WebServices
): AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading)

        try {
            val response = webServices.signIn(signInRequest)
            emit(Resource.Success(response))
        }catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = webServices.signUp(
                SignupRequest(
                    name = name,
                    email = email,
                    password = password,
                    rePassword = rePassword,
                    phone = phone
                )
            )
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}

//make user ser