package com.papierflieger.data.network.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("role")
    val role: String?
)