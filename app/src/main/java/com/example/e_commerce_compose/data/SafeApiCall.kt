package com.example.e_commerce_compose.data

import coil.network.HttpException
import com.example.e_commerce_compose.data.model.BaseResponse
import com.example.e_commerce_compose.domain.exeptions.ParsingException
import com.example.e_commerce_compose.domain.exeptions.ServerError
import com.example.e_commerce_compose.domain.exeptions.ServerTimeOutException
import com.example.e_commerce_compose.utils.Resource
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.concurrent.TimeoutException

suspend fun <T>safeApiCall(apiCall: suspend ()-> BaseResponse<T>): Flow<Resource<T>>
= flow {
    emit(Resource.Loading)
    val result =  apiCall.invoke()
    emit(Resource.Success(result.data , result.metadata))
}.catch {e->
    when(e){
        is TimeoutException ->{
            emit(Resource.Error(ServerTimeOutException(e)))
        }
        is IOException ->{
            emit(Resource.Error(ServerTimeOutException(e)))
        }
        is HttpException ->{
            val body = e.response.body.toString()
            val response = Gson().fromJson(body, BaseResponse::class.java)
            emit(
                Resource.ServerError(
                    ServerError(
                        status = response.statusMsg?:"",
                        serverMessage = response.message?:"",
                        statusCode = e.response.code
                    )
                )
            )
        }
        is JsonSyntaxException ->{
            emit(Resource.Error(ParsingException(e)))
        }
        else ->{
            emit(Resource.Error(e as Exception))
        }
    }
}