package com.papierflieger.data.network.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("regency")
    val regency: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("username")
    val username: String?
)