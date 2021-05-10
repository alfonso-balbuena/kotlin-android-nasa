package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.main.MainViewModel
import com.udacity.asteroidradar.main.MainViewModelFactory

class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory(this.requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        if(viewModel.isPhone) {
            val args : DetailFragmentArgs by navArgs()
            binding.asteroid = args.selectedAsteroid
            viewModel.selectAsteroid(null)
        } else {
            viewModel.selectedAsteroid.observe(viewLifecycleOwner, Observer {
                it?.let { asteroid ->
                    binding.asteroid = asteroid
                    binding.executePendingBindings()
                }

            })
        }

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
