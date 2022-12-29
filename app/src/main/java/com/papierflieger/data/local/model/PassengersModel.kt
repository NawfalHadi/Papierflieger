package com.papierflieger.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassengersModel(
    var passengerNames : String? = null,
    var birthDate : String? = null,
    var nationality : String? = null,
    var passportNumber : Int? = null,
    var issuingCountry : String? = null,
    var expired : String? = null,
    var nik : Int? = 0,
) : Parcelable