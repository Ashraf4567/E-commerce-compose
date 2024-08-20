package com.example.e_commerce_compose.data.repositoryImpl

import android.content.Context
import androidx.datastore.dataStore
import com.example.e_commerce_compose.data.local.UserSerializer
import com.example.e_commerce_compose.data.model.UserCredentials
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.domain.model.SignInResponse
import com.example.e_commerce_compose.domain.repository.AuthRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val Context.dataStore by dataStore("user_data.json" , UserSerializer)

class AuthRepositoryImpl(
    private val webServices: WebServices,
    private val androidContext: Context
): AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): Flow<Resource<SignInResponse>> = flow {
        emit(Resource.Loading)

        try {
            val response = webServices.signIn(signInRequest)
            emit(Resource.Success(response , null))
        }catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun saveUserToDataStore(userCredentials: UserCredentials) {
        androidContext.dataStore.updateData {
            userCredentials
        }
    }

    override suspend fun getUserFromDataStore(): Flow<UserCredentials> = flow {
        androidContext.dataStore.data.collect{
            emit(it)
        }
    }
}

//make user ser