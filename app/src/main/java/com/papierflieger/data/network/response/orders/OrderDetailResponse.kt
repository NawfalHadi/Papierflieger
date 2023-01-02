package com.papierflieger.data.network.response.orders


import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
    @SerializedName("order")
    val order: Order?
)