package com.papierflieger.data.network.response.airplane


import com.google.gson.annotations.SerializedName

data class AirplanesResponse(
    @SerializedName("dataAirplane")
    val dataAirplane: List<DataAirplane>,
    @SerializedName("message")
    val message: String
)

data class DataAirplane(
    @SerializedName("airplaneCode")
    val airplaneCode: String,
    @SerializedName("airplaneName")
    val airplaneName: String,
    @SerializedName("class")
    val category: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class AirplaneResponse(
    @SerializedName("flight")
    val flight: DataAirplane,
    @SerializedName("message")
    val message: String
)