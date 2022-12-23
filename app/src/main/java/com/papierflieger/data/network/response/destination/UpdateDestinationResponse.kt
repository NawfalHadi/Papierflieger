package com.papierflieger.data.network.response.destination


import com.google.gson.annotations.SerializedName

data class UpdateDestinationResponse(
    @SerializedName("message")
    val message: String
)