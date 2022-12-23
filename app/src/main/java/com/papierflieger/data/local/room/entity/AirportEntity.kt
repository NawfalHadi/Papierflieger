package com.papierflieger.data.local.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class AirportEntity(
    @PrimaryKey
    @ColumnInfo(name = "airportId")
    val airportId : Int,

    @ColumnInfo(name = "name")
    val name : String,
) : Parcelable