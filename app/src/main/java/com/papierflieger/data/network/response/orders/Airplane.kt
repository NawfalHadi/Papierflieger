package com.papierflieger.data.network.response.orders


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

data class From(
    @SerializedName("airportName")
    val airportName: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("cityCode")
    val cityCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class To(
    @SerializedName("airportName")
    val airportName: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("cityCode")
    val cityCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)