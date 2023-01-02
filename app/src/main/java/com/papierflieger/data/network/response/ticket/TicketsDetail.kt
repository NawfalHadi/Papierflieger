package com.papierflieger.data.network.response.ticket

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.orders.Airplane
import com.papierflieger.data.network.response.orders.From
import com.papierflieger.data.network.response.orders.To
import com.papierflieger.data.network.response.orders.Transit
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketsDetail(
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
): Parcelable
