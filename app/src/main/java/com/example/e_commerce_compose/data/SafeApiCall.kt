package com.example.e_commerce_compose.data

import coil.network.HttpException
import com.example.e_commerce_compose.data.model.BaseResponse
import com.example.e_commerce_compose.domain.exeptions.ParsingException
import com.example.e_commerce_compose.domain.exeptions.ServerError
import com.example.e_commerce_compose.domain.exeptions.ServerTimeOutException
import com.example.e_commerce_compose.utils.Resource
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException
import java.util.concurrent.TimeoutException

suspend fun <T>safeApiCall(apiCall: suspend ()-> T): Resource<T>{
    try {
        val result =  apiCall.invoke()
        return Resource.Success(result)
    } catch (e: Exception) {
        when(e){
            is TimeoutException ->{
                return Resource.Error(ServerTimeOutException(e))
            }
            is IOException ->{
                return Resource.Error(ServerTimeOutException(e))
            }
            is HttpException ->{
                val body = e.response.body.toString()
                val response = Gson().fromJson(body, BaseResponse::class.java)
                return Resource.ServerError(
                    ServerError(
                        status = response.statusMsg?:"",
                        serverMessage = response.message?:"",
                        statusCode = e.response.code
                    )
                )
            }
            is JsonSyntaxException ->{
                return Resource.Error(ParsingException(e))
            }
            else ->{
                return Resource.Error(e)
            }
        }
    }
}