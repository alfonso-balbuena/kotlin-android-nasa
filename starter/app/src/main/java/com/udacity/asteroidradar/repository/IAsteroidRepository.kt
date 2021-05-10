package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay

interface IAsteroidRepository {
    val asteroids : LiveData<List<Asteroid>>
    val pictureOfDay : LiveData<PictureOfDay>
    suspend fun fetch()
    suspend fun init()
    suspend fun clearPreviousAsteroids()
}