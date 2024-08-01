package com.example.e_commerce_compose.data.model

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("statusMsg")
    val statusMsg: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: T? = null
)