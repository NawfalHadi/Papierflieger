package com.papierflieger.data.network.response.transaction

import com.google.gson.annotations.SerializedName

data class TransactionsResponse(
    @SerializedName("transaksi")
    val transaksi: List<Transaksi>
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

data class User(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("regency")
    val regency: String?,
    @SerializedName("role")
    val role: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("verified")
    val verified: Boolean
)