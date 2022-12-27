package com.papierflieger.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelerModel(
    var names : String,
    var nik : Int
) : Parcelable