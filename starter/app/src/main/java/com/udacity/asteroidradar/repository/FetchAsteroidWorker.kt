package com.udacity.asteroidradar.repository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.imp.AsteroidRepository
import retrofit2.HttpException
import timber.log.Timber

class FetchAsteroidWorker(private val appContext : Context, private val workerParams: WorkerParameters) : CoroutineWorker(appContext,workerParams) {
    override suspend fun doWork(): Result {
        val repository = AsteroidRepository(appContext)
        return try {
            Timber.d("Cleaning previous days...")
            repository.clearPreviousAsteroids()
            Timber.d("Fetching the data...")
            repository.fetch()
            Timber.d("Success...")
            Result.success()
        } catch (exception : HttpException) {
            Result.retry()
        }
    }

}