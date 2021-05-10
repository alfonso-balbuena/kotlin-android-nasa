package com.udacity.asteroidradar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.model.Asteroid

class MainActivityViewModel() : ViewModel() {

    val isPhone = true;
    private val _selectedAsteroid : MutableLiveData<Asteroid> = MutableLiveData()
    val selectedAsteroid : LiveData<Asteroid>
    get() = _selectedAsteroid

}