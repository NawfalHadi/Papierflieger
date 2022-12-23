package com.papierflieger.data.network.response


import com.google.gson.annotations.SerializedName

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

data class DestinationResponse(
    @SerializedName("destination")
    val destination: Destination
)