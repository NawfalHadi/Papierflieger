package com.papierflieger.data.network.response.ticket


import com.google.gson.annotations.SerializedName

data class ListTicketResponse(
    @SerializedName("dataTicket")
    val dataTicket: List<DataTicket?>?
)