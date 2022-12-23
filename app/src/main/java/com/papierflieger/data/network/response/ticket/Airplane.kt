package com.papierflieger.data.network.response.ticket


import com.google.gson.annotations.SerializedName

data class Airplane(
    @SerializedName("airplaneCode")
    val airplaneCode: String?,
    @SerializedName("airplaneName")
    val airplaneName: String?,
    @SerializedName("class")
    val classX: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)