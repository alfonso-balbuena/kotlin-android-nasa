package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.repository.IAsteroidRepository
import com.udacity.asteroidradar.repository.imp.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(private val asteroidRepository: IAsteroidRepository) : ViewModel() {
    var isPhone : Boolean = true

    val pictureOfDay = asteroidRepository.pictureOfDay
    val asteroids = asteroidRepository.asteroids
    private val _selectedAsteroid = MutableLiveData<Asteroid?>()
    val selectedAsteroid : LiveData<Asteroid?>
    get() = _selectedAsteroid

    init {
        viewModelScope.launch {
            asteroidRepository.init()
        }
    }

    fun selectAsteroid(asteroid : Asteroid?) {
        _selectedAsteroid.value = asteroid
    }
}