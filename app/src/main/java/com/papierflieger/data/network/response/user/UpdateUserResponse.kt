package com.papierflieger.data.network.response.user


import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("message")
    val message: String?
)