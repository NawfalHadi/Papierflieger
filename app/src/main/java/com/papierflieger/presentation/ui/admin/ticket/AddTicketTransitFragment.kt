package com.papierflieger.presentation.ui.admin.ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.papierflieger.R
import com.papierflieger.data.network.response.airport.Airport
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.FragmentAddTicketTransitBinding
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.wrapper.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketTransitFragment : Fragment() {

    private lateinit var binding: FragmentAddTicketTransitBinding
    private val ticketViewModel : TicketViewModel by viewModels()
    private val airportViewModel : AirportsViewModel by viewModels()

    private var idTicket : Int? = null
    private var transitId : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentAddTicketTransitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNavigation()
        getEditData()
        clickListener()
        observeData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        airportViewModel.getAirports().observe(viewLifecycleOwner) { airport ->
            when (airport) {
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.apply {
                        acTransitPoint.setAdapter(
                            CustomAdapter(
                                requireContext(),
                                airport.payload?.airports
                            )
                        )
                    }
                }
            }
        }

        binding.apply {
            etTransitArrivalTime.setText("00:00")
            etTransitDepartureTime.setText("00:00")
            etTransitDuration.setText("0 jam 0 menit")

            transitArrivalTimePicker.addOnPositiveButtonClickListener {
                etTransitArrivalTime.setText(convertTimeToString(transitArrivalTimePicker.hour, transitArrivalTimePicker.minute))
            }
            transitDepartureTimePicker.addOnPositiveButtonClickListener {
                etTransitDepartureTime.setText(convertTimeToString(transitDepartureTimePicker.hour, transitDepartureTimePicker.minute))
            }
            transitDurationTimePicker.addOnPositiveButtonClickListener {
                etTransitDuration.setText(convertTimeToHour(transitDurationTimePicker.hour, transitDurationTimePicker.minute))
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun clickListener() {
        binding.apply {

            etTransitArrivalTime.keyListener = null
            etTransitDepartureTime.keyListener = null
            etTransitDuration.keyListener = null

            btnNext.setOnClickListener {
                saveData()
            }
            etTransitArrivalTime.setOnClickListener {
                transitArrivalTimePicker.show(
                    childFragmentManager,
                    "transit_arrival_time_picker"
                )
            }
            etTransitDepartureTime.setOnClickListener {
                transitDepartureTimePicker.show(
                    childFragmentManager,
                    "transit_departure_time_picker"
                )
            }
            etTransitDuration.setOnClickListener {
                transitDurationTimePicker.show(
                    childFragmentManager,
                    "transit_duration_time_picker"
                )
            }
            acTransitPoint.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as Airport
                acTransitPoint.setText("${selectedItem.airportName.toString()} (${selectedItem.cityCode.toString()})")
                transitId = selectedItem.id!!
            }
        }
    }

    private fun saveData() {
        binding.apply {
            val transitArrivalTime = etTransitArrivalTime.text.toString().trim()
            val transitDepartureTime = etTransitDepartureTime.text.toString().trim()
            val transitDuration = etTransitDuration.text.toString().trim()

            val departureDate = arguments?.getString("departureDate")
            val departureTime = arguments?.getString("departureTime")
            val idFlightFrom = arguments?.getInt("idFlightFrom")

            val arrivalDate = arguments?.getString("arrivalDate")
            val arrivalTime = arguments?.getString("arrivalTime")
            val idFlightTo = arguments?.getInt("idFlightTo")

            if (validationInput(
                    departureDate,
                    departureTime,
                    idFlightFrom,
                    arrivalDate,
                    arrivalTime,
                    idFlightTo,
                    transitArrivalTime,
                    transitDepartureTime,
                    transitDuration)
            ) {
                val dataBundle = Bundle().apply {
                    idTicket?.let { putInt("idTicket", it) }
                    putString("departureDate", departureDate)
                    putString("departureTime", departureTime)
                    idFlightFrom?.let { putInt("idFlightFrom", it) }

                    putString("arrivalDate", arrivalDate)
                    putString("arrivalTime", arrivalTime)
                    idFlightTo?.let { putInt("idFlightTo", it) }

                    putString("transitArrivalTime", transitArrivalTime)
                    putString("transitDepartureTime", transitDepartureTime)
                    putString("transitDuration", transitDuration)
                    transitId?.let { putInt("idTransit", it) }
                }
                findNavController().navigate(
                    R.id.action_addTicketTransitFragment_to_addTicketDetailFragment,
                    dataBundle
                )
            }
        }
    }

    private fun validationInput(
        departureDate: String?,
        departureTime: String?,
        idFlightFrom: Int?,
        arrivalDate: String?,
        arrivalTime: String?,
        idFlightTo: Int?,
        transitArrivalTime: String,
        transitDepartureTime: String,
        transitDuration: String
    ): Boolean {
        var isFormValid = true

        if (
            !departureDate.isNullOrEmpty() &&
            !departureTime.isNullOrEmpty() &&
            idFlightFrom != null &&
            !arrivalDate.isNullOrEmpty() &&
            !arrivalTime.isNullOrEmpty() &&
            idFlightTo != null
        ) {
            binding.apply {
                if (transitArrivalTime.isEmpty()) {
                    isFormValid = false
                    tilEtTransitArrivalTime.isErrorEnabled = true
                    tilEtTransitArrivalTime.error = "Transit Arrival Time Empty"
                } else {
                    tilEtTransitArrivalTime.isErrorEnabled = false
                }

                if (transitDepartureTime.isEmpty()) {
                    isFormValid = false
                    tilEtTransitDepartureTime.isErrorEnabled = true
                    tilEtTransitDepartureTime.error = "Transit Arrival Time Empty"
                } else {
                    tilEtTransitDepartureTime.isErrorEnabled = false
                }

                if (transitId == null) {
                    isFormValid = false
                    tilEtTransitPoint.isErrorEnabled = true
                    tilEtTransitPoint.error = "Transit Point Empty"
                } else {
                    tilEtTransitPoint.isErrorEnabled = false
                }

                if (transitDuration.isEmpty()) {
                    isFormValid = false
                    tilEtTransitDuration.isErrorEnabled = true
                    tilEtTransitDuration.error = "Transit Duration Empty"
                } else {
                    tilEtTransitDuration.isErrorEnabled = false
                }
            }
        }
        return isFormValid

    }

    private val transitArrivalTimePicker =
        MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Transit Arrival Time")
            .build()

    private val transitDepartureTimePicker =
        MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Transit Departure Time")
            .build()

    private val transitDurationTimePicker =
        MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setTitleText("Transit Duration Time")
            .build()

    private fun getEditData() {
        idTicket = arguments?.getInt("idTicket", -1)
        transitId = arguments?.getInt("idTransit", -1)
        if (idTicket != null && transitId != null && idTicket!! >= 0 && transitId!! >= 0) {
            ticketViewModel.getTicketById(idTicket!!).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        setEditData(it.payload?.ticket!!)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setEditData(ticket: DataTicket) {
        binding.apply {
            etTransitArrivalTime.setText(convertTimeFormat(ticket.arrivalTimeAtTransit.toString()))
            etTransitDepartureTime.setText(convertTimeFormat(ticket.departureTimeFromTransit.toString()))

            acTransitPoint.setText(
                convertAirport(ticket.transit?.airportName.toString(), ticket.transit?.cityCode.toString())
                , false
            )
            transitId = ticket.transitPoint

            etTransitDuration.setText(ticket.transitDuration.toString())
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}