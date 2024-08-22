package com.example.e_commerce_compose.data.local

import android.content.Context
import androidx.datastore.dataStore
import com.example.e_commerce_compose.data.model.UserCredentials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val Context.dataStore by dataStore("user_data.json" , UserSerializer)

class DataStoreManager(
    private val androidContext: Context
) {
     suspend fun saveUserToDataStore(userCredentials: UserCredentials) {
        androidContext.dataStore.updateData {
            userCredentials
        }
    }

     suspend fun getUserFromDataStore(): Flow<UserCredentials> = flow {
        androidContext.dataStore.data.collect{
            emit(it)
        }
    }
}