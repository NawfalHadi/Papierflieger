package com.papierflieger.data.network.response.airport


import com.google.gson.annotations.SerializedName

data class AirportsResponse(
    @SerializedName("airports")
    val airports: List<Airport?>?,
    @SerializedName("message")
    val message: String?
)