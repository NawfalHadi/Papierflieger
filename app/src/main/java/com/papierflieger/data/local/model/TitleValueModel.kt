package com.papierflieger.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TitleValueModel(
    val title: String,
    val value: String?
) : Parcelable