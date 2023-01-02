package com.papierflieger.data.network.response.auth


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("newUser")
    val newUser: NewUser?
)

data class NewUser(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
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
    val updatedAt: String?,
    @SerializedName("username")
    val username: String,
    @SerializedName("verified")
    val verified: Boolean?
)