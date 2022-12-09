package com.papierflieger.data.network.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("message")
    val message: String?
)