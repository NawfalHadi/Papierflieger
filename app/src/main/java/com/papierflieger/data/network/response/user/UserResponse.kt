package com.papierflieger.data.network.response.user


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(
    @SerializedName("profile")
    val profile: Profile?
)

@Parcelize
data class Profile(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: Int?,
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
    @SerializedName("role")
    val role: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("verified")
    val verified: Boolean?
) : Parcelable