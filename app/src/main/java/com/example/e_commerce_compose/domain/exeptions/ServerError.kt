package com.example.e_commerce_compose.domain.exeptions

class ServerError(
    val status: String,
    serverMessage: String,
    statusCode: Int,
): Exception(serverMessage)