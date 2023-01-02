package com.papierflieger.data.network.response.transaction

import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.user.User

data class TransactionsResponse(
    @SerializedName("transaksi")
    val transaksi: List<Transaksi>,
    @SerializedName("message")
    val message: String

)

data class Transaksi(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("orderId")
    val orderId: List<Int>,
    @SerializedName("Payment")
    val payment: Payment?,
    @SerializedName("paymentId")
    val paymentId: Int?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("tokenTransaction")
    val tokenTransaction: String,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    @SerializedName("trip")
    val trip: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: User,
    @SerializedName("userId")
    val userId: Int
)

data class Payment(
    @SerializedName("accountName")
    val accountName: String,
    @SerializedName("accountNumber")
    val accountNumber: Int,
    @SerializedName("bankName")
    val bankName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)