package com.papierflieger.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.airport.Airport

@Dao
interface AirportDao {
    @Insert
    suspend fun addAirport(airport: AirportEntity)

    @Delete
    fun removeAirport(airport: AirportEntity)

    @Query("SELECT * FROM airportentity WHERE airportId = :id LIMIT 1")
    fun selectAirport(id : Int): AirportEntity

    @Query("SELECT * FROM airportentity ORDER BY airportId ASC")
    fun showsAirport() : List<AirportEntity>

}