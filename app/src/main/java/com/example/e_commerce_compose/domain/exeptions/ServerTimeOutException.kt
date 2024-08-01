package com.example.e_commerce_compose.domain.exeptions

import java.io.IOException

class ServerTimeOutException(exception: Exception) : IOException(exception)