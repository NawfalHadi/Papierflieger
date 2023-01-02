package com.papierflieger.data.network.response.orders


import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("orderList")
    val orderList: List<Order>
)

data class Order(
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("expired")
    val expired: String?,
    @SerializedName("gate")
    val gate: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("NIK")
    val NIK: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("passengerName")
    val passengerName: String,
    @SerializedName("passportNumber")
    val passportNumber: String?,
    @SerializedName("seat")
    val seat: String?,
    @SerializedName("ticketId")
    val ticketId: List<Int>,
    @SerializedName("updatedAt")
    val updatedAt: String
)