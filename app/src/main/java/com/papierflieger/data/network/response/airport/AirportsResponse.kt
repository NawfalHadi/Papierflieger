package com.papierflieger.data.network.response.airport


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AirportsResponse(
    @SerializedName("airports")
    val airports: List<Airport?>?,
    @SerializedName("message")
    val message: String?
)

@Parcelize
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
) : Parcelable

data class AirportResponse(
    @SerializedName("airport")
    val airport: Airport
)