package com.papierflieger.data.network.response.orders


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TiketPulangDetail(
    @SerializedName("Airplane")
    val airplane: Airplane?,
    @SerializedName("airplaneId")
    val airplaneId: Int?,
    @SerializedName("arrivalDate")
    val arrivalDate: String?,
    @SerializedName("arrivalTime")
    val arrivalTime: String?,
    @SerializedName("arrivalTimeAtTransit")
    val arrivalTimeAtTransit: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("departureDate")
    val departureDate: String?,
    @SerializedName("departureTime")
    val departureTime: String?,
    @SerializedName("departureTimeFromTransit")
    val departureTimeFromTransit: String?,
    @SerializedName("flightDuration")
    val flightDuration: String?,
    @SerializedName("flightFrom")
    val flightFrom: Int?,
    @SerializedName("flightTo")
    val flightTo: Int?,
    @SerializedName("from")
    val from: From?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("ticketNumber")
    val ticketNumber: Int?,
    @SerializedName("ticketType")
    val ticketType: String?,
    @SerializedName("to")
    val to: To?,
    @SerializedName("transit")
    val transit: Transit?,
    @SerializedName("totalTransit")
    val totalTransit: Int?,
    @SerializedName("transitDuration")
    val transitDuration: String?,
    @SerializedName("transitPoint")
    val transitPoint: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
) : Parcelable