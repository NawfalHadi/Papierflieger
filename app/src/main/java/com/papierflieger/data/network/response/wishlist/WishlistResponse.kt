package com.papierflieger.data.network.response.wishlist


import com.google.gson.annotations.SerializedName
import com.papierflieger.data.network.response.destination.Destination

data class WishlistResponse(
    @SerializedName("wishlist")
    val wishlist: List<Wishlist?>
)

data class Wishlist(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Destination")
    val destination: Destination,
    @SerializedName("destinationId")
    val destinationId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)
