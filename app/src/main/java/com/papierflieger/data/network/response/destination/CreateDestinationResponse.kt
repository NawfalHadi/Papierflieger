package com.papierflieger.data.network.response.destination


import com.google.gson.annotations.SerializedName

data class CreateDestinationResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("newDestination")
    val newDestination: NewDestination
)

data class NewDestination(
    @SerializedName("airportId")
    val airportId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: List<String>,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)