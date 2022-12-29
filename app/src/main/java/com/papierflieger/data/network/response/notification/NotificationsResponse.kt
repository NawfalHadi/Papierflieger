package com.papierflieger.data.network.response.notification


import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("notifikasi")
    val notifikasi: List<Notifikasi>
)

data class Notifikasi(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("read")
    val read: Boolean,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)