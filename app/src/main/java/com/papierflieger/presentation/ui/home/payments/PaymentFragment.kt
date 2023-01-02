package com.papierflieger.presentation.ui.home.payments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.data.network.response.orders.*
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.data.network.response.ticket.TicketsDetail
import com.papierflieger.databinding.FragmentPaymentBinding
import com.papierflieger.presentation.ui.adapter.payments.FlightAdapter
import com.papierflieger.presentation.ui.adapter.payments.PassengerInformationAdapter
import com.papierflieger.wrapper.convertToRupiah
import com.papierflieger.wrapper.toTickets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var showsRecycler = false

    private lateinit var binding : FragmentPaymentBinding
    private lateinit var orderResponse: OrderResponse

    private val listOfFlight : ArrayList<TicketsDetail> = arrayListOf()
    private val flightAdapter : FlightAdapter by lazy { FlightAdapter() }

    private val listOfPassengers : ArrayList<OrderResponse> = arrayListOf()
    private val passengerAdapter : PassengerInformationAdapter by lazy { PassengerInformationAdapter() }

    private lateinit var tiketBerangkat: TiketBerangkatDetail
    private lateinit var tiketPulang: TiketPulangDetail

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderResponse = arguments?.getParcelable(ORDER_RESPONSE_KEY)!!
        bindingInformation(orderResponse)
        bindingPricing(orderResponse)
        initRecyclers()
    }

    private fun initRecyclers() {
        with(binding.rvFlight){
            flightAdapter.setItem(listOfFlight)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = flightAdapter
        }
    }

    private fun bindingPricing(orderResponse: OrderResponse) {
        with(binding){
            tvTotalPrice.text = convertToRupiah(orderResponse.price!!)
        }
    }

    private fun bindingInformation(orderResponse: OrderResponse) {
        tiketBerangkat = orderResponse.tiketBerangkat[0]
        tiketPulang = orderResponse.tiketPulang!![0]

        listOfFlight.add(tiketBerangkat.toTickets())
        listOfFlight.add(tiketPulang.toTickets())

        showsRecycler = true
    }

    companion object {
        const val ORDER_RESPONSE_KEY = "ORDER_RESPONSE_KEY"
    }
}