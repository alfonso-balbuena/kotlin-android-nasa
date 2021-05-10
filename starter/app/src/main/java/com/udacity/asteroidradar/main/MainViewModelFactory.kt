package com.udacity.asteroidradar.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.repository.imp.AsteroidRepository

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val repository = AsteroidRepository(context)
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}