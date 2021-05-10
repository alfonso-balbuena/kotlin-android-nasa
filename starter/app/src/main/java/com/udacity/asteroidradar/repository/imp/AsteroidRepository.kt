package com.udacity.asteroidradar.repository.imp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.repository.IAsteroidRepository
import com.udacity.asteroidradar.webservice.NasaService
import timber.log.Timber

class AsteroidRepository(private val context: Context) : IAsteroidRepository {
    private val service = NasaService.create()
    private val dataBase = AsteroidDataBase.getInstance(context)

    private val asteroidsToday = MutableLiveData<List<Asteroid>>()
    override val asteroids : LiveData<List<Asteroid>>
        get() = asteroidsToday

    private val _pictureToday = MutableLiveData<PictureOfDay>()
    override val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureToday

    override suspend fun init() {
        _pictureToday.value = service.getImageDay(BuildConfig.API_KEY)
        val nextSevenDays = getNextSevenDaysFormattedDates()
        val auxList = dataBase.asteroidDao().getPerDay(nextSevenDays.first(),nextSevenDays.last())
        if(auxList.isEmpty()) {
            Timber.d("Fetching data...")
            fetch()
            asteroidsToday.value = dataBase.asteroidDao().getPerDay(nextSevenDays.first(),nextSevenDays.last())
        } else {
            asteroidsToday.value = auxList
            Timber.d("Getting data from database - num of elements: ${auxList.size}")
        }
    }

    override suspend fun fetch() {
        val nextSevenDays = getNextSevenDaysFormattedDates()
        val asteroidResponse = service.getNearObjects(BuildConfig.API_KEY,nextSevenDays.first(),nextSevenDays.last())
        val asteroids = asteroidResponse.near_earth_objects.values.map {
            it.map { asteroidR -> asteroidR.convert() }
        }.flatten()
        Timber.d("Number of asteroids received ${asteroids.size}")
        dataBase.asteroidDao().clear(nextSevenDays)
        dataBase.asteroidDao().insert(asteroids)
    }


    override suspend fun clearPreviousAsteroids() {
        val nextSevenDays = getNextSevenDaysFormattedDates()
        dataBase.asteroidDao().clear(nextSevenDays)
    }
}