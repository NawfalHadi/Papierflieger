package com.papierflieger.data.network.response.notification


import com.google.gson.annotations.SerializedName

data class CreateNotificationResponse(
    @SerializedName("notifikasi")
    val notifikasi: Notifikasi
)