package com.papierflieger.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassengerInternational(
    val passengerName : String?,
    val birthDate : String?,
    val nationality : String?,
    val passportNumber: String?,
    val issuingCountry: String?,
    val expired: String?,
    val ticketId: List<Int>
): Parcelable

@Parcelize
data class PassengerDomestic(
    val passengerName : String?,
    val birthDate : String?,
    val nationality : String?,
    val expired: String?,
    val ticketId: List<Int>
): Parcelable

@Parcelize
data class OrderInternational(
    val passengers : List<PassengerInternational>
) : Parcelable

@Parcelize
data class OrderDomestic(
    val passengers : List<PassengerDomestic>
) : Parcelable