package com.papierflieger.data.network.response.airport


import com.google.gson.annotations.SerializedName

data class CreateAirportResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("newAirport")
    val newAirport: NewAirport
)

data class NewAirport(
    @SerializedName("airportName")
    val airportName: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("cityCode")
    val cityCode: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)
