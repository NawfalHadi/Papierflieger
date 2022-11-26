package com.papierflieger.presentation.ui.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.papierflieger.R
import com.papierflieger.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNavigation()
        roundTripCheck()
        switchFlightButton()

        binding.switchRoundtrip.setOnClickListener {
            roundTripCheck()
        }
    }

    private fun switchFlightButton() {
        with(binding){
            btnSwitchFlight.setOnClickListener {
                val destination  = tvDestination.text.toString()
                val from = tvFrom.text.toString()

                tvFrom.text = destination
                tvDestination.text = from
            }
        }
    }

    private fun roundTripCheck() {
        if (!binding.switchRoundtrip.isChecked) {
            with(binding){
                guidelineTwo.visibility = View.GONE
                icDateBack.visibility = View.GONE
                tvReturnDate.visibility = View.GONE
            }
        } else {
            with(binding){
                guidelineTwo.visibility = View.VISIBLE
                icDateBack.visibility = View.VISIBLE
                tvReturnDate.visibility = View.VISIBLE
            }
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}