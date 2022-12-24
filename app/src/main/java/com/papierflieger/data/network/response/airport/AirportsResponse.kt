package com.papierflieger.data.network.response.airport


import com.google.gson.annotations.SerializedName

data class AirportsResponse(
    @SerializedName("airports")
    val airports: List<Airport?>?,
    @SerializedName("message")
    val message: String?
)

data class Airport(
    @SerializedName("airportName")
    val airportName: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("cityCode")
    val cityCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class AirportResponse(
    @SerializedName("airport")
    val airport: Airport
)