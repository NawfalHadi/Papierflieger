package com.papierflieger.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingModel(
    val icon : Int?,
    val title : String,
    val subTitle : String,
    val action : Int
) : Parcelable