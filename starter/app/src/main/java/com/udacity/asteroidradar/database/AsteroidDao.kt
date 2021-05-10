package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(asteroids: List<Asteroid>)

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endData ORDER BY closeApproachDate ASC")
    suspend fun getPerDay(startDate : String,endData : String) : List<Asteroid>

    @Query("DELETE FROM Asteroid WHERE closeApproachDate NOT IN (:saveDates)")
    suspend fun clear(saveDates : List<String>)
}