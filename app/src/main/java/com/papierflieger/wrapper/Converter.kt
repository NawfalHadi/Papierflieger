package com.papierflieger.wrapper

import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val format = SimpleDateFormat(
        "EEE, dd MMM yyyy",
        Locale.getDefault()
    )
    return format.format(Date(this))
}

fun Long.toRequestDate(): String {
    val format = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )
    return format.format(Date(this))
}

fun TiketBerangkat.toDataTicket(): DataTicket {
    return DataTicket(
        airplaneId = this.airplaneId,
        arrivalDate = this.arrivalDate,
        arrivalTime = this.arrivalTime,
        arrivalTimeAtTransit = this.arrivalTimeAtTransit,
        createdAt = this.createdAt,
        departureDate = this.departureDate,
        departureTime = this.departureTime,
        departureTimeFromTransit = this.departureTimeFromTransit,
        flightDuration = this.flightDuration,
        flightFrom = this.flightFrom,
        flightTo = this.flightTo,
        id = this.id,
        price = this.price,
        ticketNumber = this.ticketNumber,
        ticketType = this.ticketType,
        totalTransit = this.totalTransit,
        transitDuration = this.transitDuration,
        transitPoint = this.transitPoint,
        updatedAt = this.updatedAt,
        from = null,
        airplane = null,
        to = null,
        transit = null
    )
}
