package com.papierflieger.presentation.ui.admin.ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.papierflieger.R
import com.papierflieger.data.network.response.airport.Airport
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.FragmentAddTicketFlightBinding
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.wrapper.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketFlightFragment : Fragment() {

    private lateinit var binding : FragmentAddTicketFlightBinding
    private val ticketViewModel : TicketViewModel by viewModels()
    private val airportViewModel : AirportsViewModel by viewModels()

    private var idTicket : Int? = null
    private var flightFromId : Int = -1
    private var flightToId : Int = -1
    private var transitId : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentAddTicketFlightBinding.inflate(layoutInflater, container, false)
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
            when(airport) {
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.apply {
                        acFlightFrom.setAdapter(CustomAdapter(requireContext(), airport.payload?.airports))
                        acFlightTo.setAdapter(CustomAdapter(requireContext(), airport.payload?.airports))
                    }
                }
            }
        }

        val dateToday = MaterialDatePicker.todayInUtcMilliseconds().toDate()
        binding.apply {
            etDepartureDate.setText(dateToday)
            etArrivalDate.setText(dateToday)

            etDepartureTime.setText("00:00")
            etArrivalTime.setText("00:00")

            dateRangePicker.addOnPositiveButtonClickListener { datePicked ->
                etDepartureDate.setText(datePicked.first.toDate())
                etArrivalDate.setText(datePicked.second.toDate())
            }
            departureTimePicker.addOnPositiveButtonClickListener {
                etDepartureTime.setText(convertTimeToString(departureTimePicker.hour, departureTimePicker.minute))
            }
            arrivalTimePicker.addOnPositiveButtonClickListener {
                etArrivalTime.setText(convertTimeToString(arrivalTimePicker.hour, arrivalTimePicker.minute))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickListener() {
        binding.apply {

            etDepartureDate.keyListener = null
            etArrivalDate.keyListener = null
            etDepartureTime.keyListener = null
            etArrivalTime.keyListener = null

            btnNext.setOnClickListener {
                saveData()
            }
            etDepartureDate.setOnClickListener {
                dateRangePicker.show(
                    childFragmentManager,
                    "date_range_picker"
                )
            }
            etDepartureTime.setOnClickListener {
                departureTimePicker.show(
                    childFragmentManager,
                    "departure_time_picker"
                )
            }
            etArrivalDate.setOnClickListener {
                dateRangePicker.show(
                    childFragmentManager,
                    "date_range_picker"
                )
            }
            etArrivalTime.setOnClickListener {
                arrivalTimePicker.show(
                    childFragmentManager,
                    "arrival_time_picker"
                )
            }
            acFlightFrom.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as Airport
                acFlightFrom.setText("${selectedItem.airportName.toString()} (${selectedItem.cityCode.toString()})")
                flightFromId = selectedItem.id!!
            }
            acFlightTo.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as Airport
                acFlightTo.setText("${selectedItem.airportName.toString()} (${selectedItem.cityCode.toString()})")
                flightToId = selectedItem.id!!
            }
        }
    }

    private val dateRangePicker =
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Departure Date and Arrival Date")
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

    private val departureTimePicker =
        MaterialTimePicker.Builder()
            .setInputMode(INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Departure Time")
            .build()

    private val arrivalTimePicker =
        MaterialTimePicker.Builder()
            .setInputMode(INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Departure Time")
            .build()

    private fun saveData() {
        binding.apply {
            val departureDate = etDepartureDate.text.toString().trim()
            val departureTime = etDepartureTime.text.toString().trim()

            val arrivalDate = etArrivalDate.text.toString().trim()
            val arrivalTime = etArrivalTime.text.toString().trim()

            if (validationInput(departureDate, departureTime, flightFromId, arrivalDate, arrivalTime, flightToId)) {
                val dataBundle = Bundle().apply {
                    idTicket?.let { putInt("idTicket", it) }
                    putString("departureDate", departureDate)
                    putString("departureTime", departureTime)
                    putInt("idFlightFrom", flightFromId)

                    putString("arrivalDate", arrivalDate)
                    putString("arrivalTime", arrivalTime)
                    putInt("idFlightTo", flightToId)
                    if (binding.switchTransit.isChecked) transitId?.let { putInt("idTransit", it) }
                }
                if (binding.switchTransit.isChecked) {
                    findNavController().navigate(
                        R.id.action_addTicketFlightFragment_to_addTicketTransitFragment,
                        dataBundle
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_addTicketFlightFragment_to_addTicketDetailFragment,
                        dataBundle
                    )
                }
            }
        }
    }

    private fun validationInput(
        departureDate: String,
        departureTime: String,
        IdFlightFrom: Int,
        arrivalDate: String,
        arrivalTime: String,
        IdFlightTo: Int
    ): Boolean {
        var isFormValid = true
        binding.apply {
            if (departureDate.isEmpty()) {
                isFormValid = false
                tilEtDepartureDate.isErrorEnabled = true
                tilEtDepartureDate.error = "Departure Date Empty"
            } else {
                tilEtDepartureDate.isErrorEnabled = false
            }

            if (departureTime.isEmpty()) {
                isFormValid = false
                tilEtDepartureTime.isErrorEnabled = true
                tilEtDepartureTime.error = "Departure Time Empty"
            } else {
                tilEtDepartureTime.isErrorEnabled = false
            }

            if (IdFlightFrom <= 0) {
                isFormValid = false
                tilEtFlightFrom.isErrorEnabled = true
                tilEtFlightFrom.error = "Flight From Empty"
            } else {
                tilEtFlightFrom.isErrorEnabled = false
            }

            if (arrivalDate.isEmpty()) {
                isFormValid = false
                tilEtArrivalDate.isErrorEnabled = true
                tilEtArrivalDate.error = "Arrival Date Empty"
            } else {
                tilEtArrivalDate.isErrorEnabled = false
            }

            if (arrivalTime.isEmpty()) {
                isFormValid = false
                tilEtArrivalTime.isErrorEnabled = true
                tilEtArrivalTime.error = "Arrival Time Empty"
            } else {
                tilEtArrivalTime.isErrorEnabled = false
            }

            if (IdFlightTo <= 0) {
                isFormValid = false
                tilEtFlightTo.isErrorEnabled = true
                tilEtFlightTo.error = "Flight To Empty"
            } else {
                tilEtFlightTo.isErrorEnabled = false
            }

        }
        return isFormValid
    }

    private fun getEditData() {
        idTicket = arguments?.getInt("id")
        if (idTicket != null) {
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
            etDepartureDate.setText(convertDateFormat(ticket.departureDate.toString()))
            etDepartureTime.setText(convertTimeFormat(ticket.departureTime.toString()))

            acFlightFrom.setText(
                convertAirport(ticket.from?.airportName.toString(), ticket.from?.cityCode.toString())
                , false)
            flightFromId = ticket.flightFrom!!

            etArrivalDate.setText(convertDateFormat(ticket.arrivalDate.toString()))
            etArrivalTime.setText(convertTimeFormat(ticket.arrivalTime.toString()))

            acFlightTo.setText(
                convertAirport(ticket.to?.airportName.toString(), ticket.to?.cityCode.toString())
                , false)
            flightToId = ticket.flightTo!!

            if (ticket.transitPoint != null) {
                transitId = ticket.transitPoint
                switchTransit.isChecked = true
            } else {
                switchTransit.isChecked = false
            }
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}