package com.papierflieger.data.network.response.orders


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.data.network.response.ticket.TiketPulang
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("passengers")
    val passengers: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("tiketBerangkat")
    val tiketBerangkat: List<TiketBerangkat>?,
    @SerializedName("tiketPulang")
    val tiketPulang: List<TiketPulang>?,
    @SerializedName("tokenTransaction")
    val tokenTransaction: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?
) : Parcelable