package com.papierflieger.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.papierflieger.data.local.room.dao.AirportDao
import com.papierflieger.data.local.room.entity.AirportEntity

@Database(entities = [AirportEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun airportDao() : AirportDao

    companion object {
        private const val DB_NAME = "local_storage.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(ctx: Context): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class.java) {

                val instances = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java, DB_NAME)
                    .build()

//                INSTANCE = instances
                instances
            }
        }
    }
}