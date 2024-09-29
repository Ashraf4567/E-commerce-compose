package com.example.e_commerce_compose.utils

import com.example.e_commerce_compose.data.model.Metadata


sealed class Resource<out T> {
    class Success<T>(val data: T?): Resource<T>()
    class Error(val error: Exception) : Resource<Nothing>()
    data class ServerError(val error: com.example.e_commerce_compose.domain.exeptions.ServerError): Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}