package com.papierflieger.data.network.response.ticket


import com.google.gson.annotations.SerializedName

data class CreateTicketResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("newTicket")
    val newTicket: NewTicket
)

data class NewTicket(
    @SerializedName("airplaneId")
    val airplaneId: Int,
    @SerializedName("arrivalDate")
    val arrivalDate: String,
    @SerializedName("arrivalTime")
    val arrivalTime: String,
    @SerializedName("arrivalTimeAtTransit")
    val arrivalTimeAtTransit: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("departureDate")
    val departureDate: String,
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("departureTimeFromTransit")
    val departureTimeFromTransit: String?,
    @SerializedName("flightDuration")
    val flightDuration: String,
    @SerializedName("flightFrom")
    val flightFrom: Int,
    @SerializedName("flightTo")
    val flightTo: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("ticketNumber")
    val ticketNumber: Int,
    @SerializedName("ticketType")
    val ticketType: String,
    @SerializedName("totalTransit")
    val totalTransit: Int?,
    @SerializedName("transitDuration")
    val transitDuration: String?,
    @SerializedName("transitPoint")
    val transitPoint: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String
)