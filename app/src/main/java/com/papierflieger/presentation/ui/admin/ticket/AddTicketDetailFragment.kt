package com.papierflieger.presentation.ui.admin.ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.papierflieger.R
import com.papierflieger.data.network.response.airplane.DataAirplane
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.FragmentAddTicketDetailBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.AirplaneViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.wrapper.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketDetailFragment : Fragment() {

    private lateinit var binding: FragmentAddTicketDetailBinding
    private val adminViewModel : AdminViewModel by viewModels()
    private val ticketViewModel : TicketViewModel by viewModels()
    private val airplaneViewModel : AirplaneViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private var idTicket : Int? = null
    private var airplaneId : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentAddTicketDetailBinding.inflate(layoutInflater, container, false)
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
        airplaneViewModel.getAirplanes().observe(viewLifecycleOwner) { airplane ->
            when (airplane) {
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.apply {
                        acAirplaneName.setAdapter(
                            CustomAdapterAirplane(
                                requireContext(),
                                airplane.payload?.dataAirplane
                            )
                        )
                    }
                }
            }
        }

        binding.apply {

            val ticketType = arrayOf("Internasional", "Domestik")
            binding.acTicketType.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ticketType))

            etFlightDuration.setText("0 jam 0 menit")

            flightDurationTimePicker.addOnPositiveButtonClickListener {
                etFlightDuration.setText(convertTimeToHour(flightDurationTimePicker.hour, flightDurationTimePicker.minute))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickListener() {
        binding.apply {

            etFlightDuration.keyListener = null

            btnSave.setOnClickListener {
                saveData()
            }
            etFlightDuration.setOnClickListener {
                flightDurationTimePicker.show(
                    childFragmentManager,
                    "flight_duration_time_picker"
                )
            }
            acAirplaneName.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as DataAirplane
                acAirplaneName.setText("${selectedItem.airplaneName} (${selectedItem.airplaneCode})")
                airplaneId = selectedItem.id
            }
        }
    }

    private fun saveData() {
        binding.apply {
            val ticketNumber = etTicketNumber.text.toString().trim()
            val flightDuration = etFlightDuration.text.toString().trim()
            val ticketType = acTicketType.text.toString().trim()
            val price = etTicketPrice.text.toString().trim()

            val departureDate = arguments?.getString("departureDate")
                ?.let { convertDateFormatUpload(it) }
            val departureTime = arguments?.getString("departureTime")
            val idFlightFrom = arguments?.getInt("idFlightFrom")

            val arrivalDate = arguments?.getString("arrivalDate")
                ?.let { convertDateFormatUpload(it) }
            val arrivalTime = arguments?.getString("arrivalTime")
            val idFlightTo = arguments?.getInt("idFlightTo")

            val transitArrivalTime = arguments?.getString("transitArrivalTime")
            val transitDepartureTime = arguments?.getString("transitDepartureTime")
            val transitDuration = arguments?.getString("transitDuration")
            val idTransit = arguments?.getInt("idTransit", -1)

            if (validationInput(
                    ticketNumber,
                    flightDuration,
                    ticketType,
                    price,
                    departureDate,
                    departureTime,
                    idFlightFrom,
                    arrivalDate,
                    arrivalTime,
                    idFlightTo,
                    transitArrivalTime,
                    transitDepartureTime,
                    transitDuration,
                    idTransit)
            ) {
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    if (idTicket != null && idTicket!! >= 0) {
                        if (idTransit != null && idTransit >= 0) {
                            adminViewModel.updateTicket(
                                idTicket!!,
                                token,
                                ticketNumber.toInt(),
                                departureDate!!,
                                departureTime!!,
                                arrivalDate!!,
                                arrivalTime!!,
                                idFlightFrom!!,
                                idFlightTo!!,
                                airplaneId!!,
                                price.toInt(),
                                1,
                                idTransit,
                                transitDuration!!,
                                ticketType,
                                flightDuration,
                                transitArrivalTime!!,
                                transitDepartureTime!!
                            ).observe(viewLifecycleOwner) {
                                when (it) {
                                    is Resource.Empty -> {}
                                    is Resource.Error -> {}
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        findNavController().navigate(R.id.adminTicketFragment)
                                    }
                                }
                            }
                        } else {
                            adminViewModel.updateTicket(
                                idTicket!!,
                                token,
                                ticketNumber.toInt(),
                                departureDate!!,
                                departureTime!!,
                                arrivalDate!!,
                                arrivalTime!!,
                                idFlightFrom!!,
                                idFlightTo!!,
                                airplaneId!!,
                                price.toInt(),
                                null,
                                null,
                                null,
                                ticketType,
                                flightDuration,
                                null,
                                null
                            ).observe(viewLifecycleOwner) {
                                when (it) {
                                    is Resource.Empty -> {}
                                    is Resource.Error -> {}
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        findNavController().navigate(R.id.adminTicketFragment)
                                    }
                                }
                            }
                        }
                    } else {
                        if (idTransit != null && idTransit >= 0) {
                            adminViewModel.createTicketTransit(
                                token,
                                ticketNumber.toInt(),
                                departureDate!!,
                                departureTime!!,
                                arrivalDate!!,
                                arrivalTime!!,
                                idFlightFrom!!,
                                idFlightTo!!,
                                airplaneId!!,
                                price.toInt(),
                                1,
                                idTransit,
                                transitDuration!!,
                                ticketType,
                                flightDuration,
                                transitArrivalTime!!,
                                transitDepartureTime!!
                            ).observe(viewLifecycleOwner) {
                                when (it) {
                                    is Resource.Empty -> {}
                                    is Resource.Error -> {}
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        findNavController().navigate(R.id.adminTicketFragment)
                                    }
                                }
                            }
                        } else {
                            adminViewModel.createTicketDirect(
                                token,
                                ticketNumber.toInt(),
                                departureDate!!,
                                departureTime!!,
                                arrivalDate!!,
                                arrivalTime!!,
                                idFlightFrom!!,
                                idFlightTo!!,
                                airplaneId!!,
                                price.toInt(),
                                ticketType,
                                flightDuration
                            ).observe(viewLifecycleOwner) {
                                when (it) {
                                    is Resource.Empty -> {}
                                    is Resource.Error -> {}
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        findNavController().navigate(R.id.adminTicketFragment)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validationInput(
        ticketNumber: String,
        flightDuration: String,
        ticketType: String,
        price: String,
        departureDate: String?,
        departureTime: String?,
        idFlightFrom: Int?,
        arrivalDate: String?,
        arrivalTime: String?,
        idFlightTo: Int?,
        transitArrivalTime: String?,
        transitDepartureTime: String?,
        transitDuration: String?,
        idTransit: Int?
    ): Boolean {
        var isFormValid: Boolean

        if (idTransit != null && idTransit >= 0) {
            isFormValid = !departureDate.isNullOrEmpty() &&
                    !departureTime.isNullOrEmpty() &&
                    idFlightFrom != null &&
                    !arrivalDate.isNullOrEmpty() &&
                    !arrivalTime.isNullOrEmpty() &&
                    idFlightTo != null &&
                    !transitArrivalTime.isNullOrEmpty() &&
                    !transitDepartureTime.isNullOrEmpty() &&
                    !transitDuration.isNullOrEmpty()
        } else {
            isFormValid = !departureDate.isNullOrEmpty() &&
                    !departureTime.isNullOrEmpty() &&
                    idFlightFrom != null &&
                    !arrivalDate.isNullOrEmpty() &&
                    !arrivalTime.isNullOrEmpty() &&
                    idFlightTo != null
        }

        if (
            !departureDate.isNullOrEmpty() &&
            !departureTime.isNullOrEmpty() &&
            idFlightFrom != null &&
            !arrivalDate.isNullOrEmpty() &&
            !arrivalTime.isNullOrEmpty() &&
            idFlightTo != null &&
            transitArrivalTime.isNullOrEmpty() &&
            transitDepartureTime.isNullOrEmpty() &&
            transitDuration.isNullOrEmpty()
        ) {
            binding.apply {
                if (ticketNumber.isEmpty()) {
                    isFormValid = false
                    tilEtTicketNumber.isErrorEnabled = true
                    tilEtTicketNumber.error = "Ticket Number Empty"
                } else {
                    tilEtTicketNumber.isErrorEnabled = false
                }

                if (flightDuration.isEmpty()) {
                    isFormValid = false
                    tilEtFlightDuration.isErrorEnabled = true
                    tilEtFlightDuration.error = "Flight Duration Empty"
                } else {
                    tilEtFlightDuration.isErrorEnabled = false
                }

                if (airplaneId == null) {
                    isFormValid = false
                    tilEtAirplaneName.isErrorEnabled = true
                    tilEtAirplaneName.error = "Airplane Name Empty"
                } else {
                    tilEtAirplaneName.isErrorEnabled = false
                }

                if (ticketType.isEmpty()) {
                    isFormValid = false
                    tilEtTicketType.isErrorEnabled = true
                    tilEtTicketType.error = "Ticket Type Empty"
                } else {
                    tilEtTicketType.isErrorEnabled = false
                }
                if (price.isEmpty()) {
                    isFormValid = false
                    tilEtTicketPrice.isErrorEnabled = true
                    tilEtTicketPrice.error = "Price Empty"
                } else {
                    tilEtTicketPrice.isErrorEnabled = false
                }
            }
        }
        return isFormValid
    }

    private val flightDurationTimePicker =
        MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setTitleText("Flight Duration Time")
            .build()

    private fun getEditData() {
        idTicket = arguments?.getInt("idTicket", -1)
        if (idTicket != null && idTicket!! >= 0) {
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
            etTicketNumber.setText(ticket.ticketNumber.toString())
            etFlightDuration.setText(ticket.flightDuration.toString())

            acTicketType.setText(
                ticket.ticketType.toString()
                , false
            )
            acAirplaneName.setText(
                convertAirport(ticket.airplane?.airplaneName.toString(), ticket.airplane?.airplaneCode.toString())
                , false
            )
            airplaneId = ticket.airplaneId

            etTicketPrice.setText(ticket.price.toString())
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}