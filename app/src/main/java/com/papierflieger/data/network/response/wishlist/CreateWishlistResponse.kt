package com.papierflieger.data.network.response.wishlist


import com.google.gson.annotations.SerializedName

data class CreateWishlistResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("newWishlist")
    val newWishlist: NewWishlist
)

data class NewWishlist(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("destinationId")
    val destinationId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)