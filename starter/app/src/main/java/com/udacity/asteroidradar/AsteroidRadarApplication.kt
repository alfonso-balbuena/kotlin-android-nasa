package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.repository.FetchAsteroidWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {

    private val TAG = "FETCH_DATA_WORKER"

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresDeviceIdle(true)
            .build()
        val fetchData = PeriodicWorkRequestBuilder<FetchAsteroidWorker>(15,TimeUnit.MINUTES)
            .addTag(TAG)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(TAG,ExistingPeriodicWorkPolicy.KEEP,fetchData)
    }


}