package com.papierflieger.data.network.response.ticket


import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.airplane.DataAirplane
import com.papierflieger.data.network.response.airport.Airport

data class ListTicketResponse(
    @SerializedName("dataTicket")
    val dataTicket: List<DataTicket?>?
)

data class DataTicket(
    @SerializedName("Airplane")
    val airplane: DataAirplane?,
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
    val from: Airport?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("ticketNumber")
    val ticketNumber: Int?,
    @SerializedName("ticketType")
    val ticketType: String?,
    @SerializedName("to")
    val to: Airport?,
    @SerializedName("totalTransit")
    val totalTransit: Int?,
    @SerializedName("transit")
    val transit: Airport?,
    @SerializedName("transitDuration")
    val transitDuration: String?,
    @SerializedName("transitPoint")
    val transitPoint: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class TicketResponse(
    @SerializedName("ticket")
    val ticket: DataTicket
)