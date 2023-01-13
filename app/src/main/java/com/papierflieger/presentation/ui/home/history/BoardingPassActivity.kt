package com.papierflieger.presentation.ui.home.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.databinding.FragmentBoardingPassBinding
import com.papierflieger.wrapper.convertDateFormat
import com.papierflieger.wrapper.convertTimeFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardingPassActivity : AppCompatActivity() {

    private lateinit var binding : FragmentBoardingPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBoardingPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mBundle = intent.extras

        try {
            val ticket = mBundle?.getParcelable<Ticket>(DetailHistoryActivity.TICKET_LIST_KEY)
            bindingInformation(ticket)
        } catch (e : Exception) {}
    }

    private fun bindingInformation(ticket: Ticket?) {
        with(binding){
            textTicketCode.text = ticket?.ticketNumber.toString()

            tvFromlocation.text = ticket?.from?.city.toString()
            textAirportDeparture.text = ticket?.from?.city
            textAirportDetail.text = ticket?.from?.airportName

            tvDestinationlocation.text = ticket?.to?.city.toString()
            textAirportArrival.text = ticket?.to?.city
            textAirportArrivalDetail.text = ticket?.to?.airportName

            tvPlaneCode.text = ticket?.airplane?.airplaneCode

            tvOrdernumber.text = ticket?.ticketNumber.toString()

            textTimeDeparture.text = convertTimeFormat(ticket?.departureTime.toString())
            textDateDeparture.text = convertDateFormat(ticket?.departureDate.toString())

            textTime.text = ticket?.flightDuration.toString()

            textTimeArrival.text = convertTimeFormat(ticket?.arrivalTime.toString())
            textDateArrival.text = convertDateFormat(ticket?.arrivalDate.toString())

        }

    }
}