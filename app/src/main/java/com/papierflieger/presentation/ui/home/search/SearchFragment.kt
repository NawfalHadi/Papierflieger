package com.papierflieger.presentation.ui.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.papierflieger.databinding.FragmentSearchBinding
import com.papierflieger.wrapper.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNavigation()
        roundTripCheck()
        switchFlightButton()
        changeValue()
        clickListener()
    }

    private fun changeValue() {
        val dateToday = MaterialDatePicker.todayInUtcMilliseconds().toDate()
        binding.tvDateDeparture.text = dateToday
        binding.tvDateReturn.text = dateToday

        dateRangePicker.addOnPositiveButtonClickListener { datePicked ->
            binding.tvDateDeparture.text = datePicked.first.toDate()
            binding.tvDateReturn.text = datePicked.second.toDate()
        }

        datePicker.addOnPositiveButtonClickListener { datePicked ->
            binding.tvDateDeparture.text = datePicked.toDate()
            binding.tvDateReturn.text = datePicked.toDate()
        }
    }

    private fun clickListener() {
        binding.switchRoundtrip.setOnClickListener {
            roundTripCheck()
        }

        binding.llDateDeparture.setOnClickListener {
            if (!binding.switchRoundtrip.isChecked) {
                datePicker.show(
                    childFragmentManager,
                    "date_picker"
                )
            } else {
                dateRangePicker.show(
                    childFragmentManager,
                    "date_range_picker"
                )
            }
        }

        binding.llDateReturn.setOnClickListener {
            dateRangePicker.show(
                childFragmentManager,
                "date_range_picker"
            )
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
                llDateReturn.visibility = View.GONE
            }
        } else {
            with(binding){
                guidelineTwo.visibility = View.VISIBLE
                llDateReturn.visibility = View.VISIBLE
            }
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("One Way")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(MaterialDatePicker.thisMonthInUtcMilliseconds())
                    .setValidator(DateValidatorPointForward.now())
                    .build()
            )
            .build()

    private val dateRangePicker =
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Round Trip")
            .setSelection(
                Pair(
                    MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(MaterialDatePicker.thisMonthInUtcMilliseconds())
                    .setValidator(DateValidatorPointForward.now())
                    .build()
            )
            .build()
}