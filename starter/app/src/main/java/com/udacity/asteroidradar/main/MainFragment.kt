package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.viewModel.MainActivityViewModel
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory(this.requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        val adapter = AsteroidAdapter(AsteroidListener {
            viewModel.selectAsteroid(it)
        })
        viewModel.selectedAsteroid.observe(viewLifecycleOwner, {
            it?.let {
                if(viewModel.isPhone) {
                    val action = MainFragmentDirections.actionShowDetail(it)
                    findNavController().navigate(action)
                }
            }
        })

        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroids.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.pictureOfDay.observe(viewLifecycleOwner, {
            if (it.mediaType == "image") {
                Picasso.get().load(it.url).into(binding.activityMainImageOfTheDay)
                binding.activityMainImageOfTheDay.contentDescription = it.title
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
