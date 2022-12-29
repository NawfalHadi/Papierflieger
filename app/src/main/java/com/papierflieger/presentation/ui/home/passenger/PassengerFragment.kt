package com.papierflieger.presentation.ui.home.passenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.data.local.model.PassengersModel
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.FragmentTransactionFlightBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.passenger.TravelerAdapter
import com.papierflieger.presentation.ui.adapter.tickets.TicketsAdapter
import com.papierflieger.presentation.ui.home.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PassengerFragment : Fragment(){
    private var passengerCounter : Int = 1
    private var listAdded : Boolean = false
    private var ticketsPreview : ArrayList<DataTicket> = arrayListOf()

    private val sessionViewModel : SessionViewModel by viewModels()

    private lateinit var binding : FragmentTransactionFlightBinding

    private val ticketAdapter : TicketsAdapter by lazy { TicketsAdapter() }
    private val travelerAdapter : TravelerAdapter by lazy { TravelerAdapter() }

    private val listOfPassenger : ArrayList<String> = arrayListOf()
    private val listObjectInformation : ArrayList<PassengersModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionFlightBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idCustomer = findNavController().currentBackStackEntry?.savedStateHandle?.get<Int>(SearchActivity.PASSENGER_COUNTER_KEY)
        val customer = findNavController().currentBackStackEntry?.savedStateHandle?.get<PassengersModel>(SearchActivity.PASSENGER_KEY)

        setupData()
        setupPassengerItem()
        setCustomerData(customer, idCustomer)
        showsRecycler()

        binding.btnPay.setOnClickListener {
            for (position in 1..listObjectInformation.size){
                try {
                    if (listObjectInformation[position].passengerNames.isNullOrEmpty()){
                        Toast.makeText(context, "Passenger $position still not filled", Toast.LENGTH_SHORT).show()
                    }
                } catch (e : Exception) {

                }

            }
        }

        sessionViewModel.getToken().observe(viewLifecycleOwner){
            if (it!=""){
                loginAlready()
            } else {
                haventLogin()
            }
        }
    }

    private fun setCustomerData(customer: PassengersModel?, idCustomer: Int? = 0) {
        Log.e("size", listObjectInformation.size.toString())
        Log.e("data", idCustomer.toString())


        try {
            listObjectInformation[idCustomer!!] = customer!!
            Log.e("CUSTOMER ", listObjectInformation[idCustomer].toString())
        } catch (e : Exception) {
            Log.e("as", e.toString())
        }
    }


    private fun setupData() {
        ticketsPreview = arguments?.getParcelableArrayList(SearchActivity.TICKETS_KEY)!!
        passengerCounter = arguments?.getInt(SearchActivity.PASSENGER_COUNTER_KEY, 1) ?: 1
    }

    private fun showsRecycler() {
        with(binding.rvProduct){
            ticketAdapter.setItem(ticketsPreview)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = ticketAdapter
        }

        with(binding.rvTraveler){
            travelerAdapter.setItem(listOfPassenger)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = travelerAdapter
        }

        travelerAdapter.listener(object : TravelerAdapter.OnTravelCardListener {
            override fun gotoForm(position: Int) {
                val mBundle = Bundle()
                mBundle.putInt(SearchActivity.PASSENGER_COUNTER_KEY, position)
                mBundle.putParcelable(SearchActivity.PASSENGER_KEY, listObjectInformation[position])
                findNavController().navigate(R.id.action_passengerFragment_to_nationalFormFragment, mBundle)
            }
        })
    }

    private fun setupPassengerItem() {
        if (!listAdded){
            for (i in 1..passengerCounter){
                listOfPassenger.add("Passenger $i")
                listObjectInformation.add(PassengersModel())
            }
        }

        listAdded = true
    }

    private fun haventLogin() {
        binding.cvLogin.apply {
            visibility = View.VISIBLE
            isClickable = false
        }
        binding.cvCustomer.apply {
            visibility = View.INVISIBLE
            isClickable = true
        }
    }

    private fun loginAlready() {
        binding.cvLogin.apply {
            visibility = View.INVISIBLE
            isClickable = true
        }
        binding.cvCustomer.apply {
            visibility = View.VISIBLE
            isClickable = false
        }
    }
}