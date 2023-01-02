package com.papierflieger.data.network.response.transaction


import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.orders.Airplane
import com.papierflieger.data.network.response.orders.From
import com.papierflieger.data.network.response.orders.To
import com.papierflieger.data.network.response.orders.Transit

data class HistoriesResponse(
    @SerializedName("order")
    val order: List<Order?>?,
    @SerializedName("ticket")
    val ticket: List<Ticket?>?,
    @SerializedName("transaction")
    val transaction: List<Transaction?>?
)

data class Order(
    @SerializedName("birthDate")
    val birthDate: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("expired")
    val expired: String?,
    @SerializedName("gate")
    val gate: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("NIK")
    val nIK: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("passengerName")
    val passengerName: String?,
    @SerializedName("passportNumber")
    val passportNumber: String?,
    @SerializedName("seat")
    val seat: Any?,
    @SerializedName("ticketId")
    val ticketId: List<Int?>?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class Transaction(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("orderId")
    val orderId: List<Int?>?,
    @SerializedName("paymentId")
    val paymentId: Int?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("tokenTransaction")
    val tokenTransaction: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("trip")
    val trip: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)

data class Ticket(
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
    @SerializedName("totalTransit")
    val totalTransit: Int?,
    @SerializedName("transit")
    val transit: Transit?,
    @SerializedName("transitDuration")
    val transitDuration: String?,
    @SerializedName("transitPoint")
    val transitPoint: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)