package com.papierflieger.data.network.response.airplane


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AirplanesResponse(
    @SerializedName("dataAirplane")
    val dataAirplane: List<DataAirplane>,
    @SerializedName("message")
    val message: String
)

@Parcelize
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
) : Parcelable

data class AirplaneResponse(
    @SerializedName("flight")
    val flight: DataAirplane,
    @SerializedName("message")
    val message: String
)