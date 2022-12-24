package com.papierflieger.presentation.ui.home.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.papierflieger.R
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.databinding.FragmentSearchBinding
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.DatasyncViewModel
import com.papierflieger.presentation.ui.home.search.bottomsheet.SearchAirportBottomSheet
import com.papierflieger.wrapper.Resource
import com.papierflieger.wrapper.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding

    private val airportViewModel : AirportsViewModel by viewModels()
    private val datasyncViewModel : DatasyncViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        syncingDataToRoom()

        allNavigation()
        roundTripCheck()
        switchFlightButton()
        changeValue()
        clickListener()
    }

    private fun syncingDataToRoom() {
        datasyncViewModel.isAirportSync().observe(viewLifecycleOwner){
            airportViewModel.syncAirportsData(!it).observe(viewLifecycleOwner){ airports ->
                when(airports){
                    is Resource.Success -> {
                        val airport = airports.payload?.airports
                        for (i in airport!!){
                            Log.e("Airport", i?.airportName.toString())
                            airportViewModel.addAirports(AirportEntity(
                                airportId = i?.id!!,
                                name = i.airportName!!,
                                city = i.city!!,
                                cityCode = i.cityCode!!
                            ))
                        }
                        datasyncViewModel.airportSynced()
                    }
                }
            }
        }
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
        binding.tvFrom.setOnClickListener {
            val currentDialog = parentFragmentManager.findFragmentByTag(SearchAirportBottomSheet::class.java.simpleName)
            if (currentDialog == null) {
                SearchAirportBottomSheet(object : SearchAirportBottomSheet.OnAirportClickListner {
                    @SuppressLint("SetTextI18n")
                    override fun itemChoosed(airport: AirportEntity) {
                        val city = airport.city.split(",")
                        binding.tvFrom.text = "${city[0]} (${airport.cityCode})"
                    }
                }).show(parentFragmentManager, SearchAirportBottomSheet::class.java.simpleName)
            }
        }

        binding.tvDestination.setOnClickListener {
            val currentDialog = parentFragmentManager.findFragmentByTag(SearchAirportBottomSheet::class.java.simpleName)
            if (currentDialog == null) {
                SearchAirportBottomSheet(object : SearchAirportBottomSheet.OnAirportClickListner {
                    @SuppressLint("SetTextI18n")
                    override fun itemChoosed(airport: AirportEntity) {
                        val city = airport.city.split(",")
                        binding.tvDestination.text = "${city[0]} (${airport.cityCode})"
                    }
                }).show(parentFragmentManager, SearchAirportBottomSheet::class.java.simpleName)
            }
        }

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

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_listFlightFragment)
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