package com.papierflieger.data.network.response.airplane


import com.google.gson.annotations.SerializedName

data class CreateAirplaneResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("newFlight")
    val newFlight: NewFlight
)

data class NewFlight(
    @SerializedName("airplaneCode")
    val airplaneCode: String,
    @SerializedName("airplaneName")
    val airplaneName: String,
    @SerializedName("class")
    val classX: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)
