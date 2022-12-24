package com.papierflieger.data.network.response.destination


import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.airport.Airport

data class DestinationsResponse(
    @SerializedName("destinations")
    val destinations: List<Destination?>?
)

data class Destination(
    @SerializedName("Airport")
    val airport: Airport?,
    @SerializedName("airportId")
    val airportId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: List<String?>?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class DestinationResponse(
    @SerializedName("destination")
    val destination: Destination
)