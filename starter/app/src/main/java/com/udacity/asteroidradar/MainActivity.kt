package com.udacity.asteroidradar

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.databinding.ActivityMainBinding
import com.udacity.asteroidradar.main.MainViewModel
import com.udacity.asteroidradar.main.MainViewModelFactory
import com.udacity.asteroidradar.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels { MainViewModelFactory(this.applicationContext) }
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.fragmentDetail?.let {
            viewModel.isPhone = false
        }
        setContentView(binding.root)
    }
}
