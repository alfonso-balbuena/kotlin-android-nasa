package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {
    abstract fun asteroidDao() : AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDataBase? = null

        fun getInstance(context : Context) : AsteroidDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                                AsteroidDataBase::class.java,"asteroid_data_base").fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}