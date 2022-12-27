package com.papierflieger.presentation.ui.home.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.data.local.model.TravelerModel
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.FragmentTransactionFlightBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.passenger.TravelerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerFragment : Fragment(){
    private val sessionViewModel : SessionViewModel by viewModels()

    private lateinit var binding : FragmentTransactionFlightBinding
    private lateinit var ticketsPreview : ArrayList<DataTicket>

    private val travelerAdapter : TravelerAdapter by lazy { TravelerAdapter() }
    private val listOfPassenger : ArrayList<String> = arrayListOf()
    private val listObjectInformation : ArrayList<TravelerModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionFlightBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPassengerItem()
        showsRecycler()
        sessionViewModel.getToken().observe(viewLifecycleOwner){
            if (it!=""){
                loginAlready()
            } else {
                haventLogin()
            }
        }
    }

    private fun showsRecycler() {
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
                mBundle.putInt(PassengerActivity.PASSENGER_KEY, position)
                findNavController().navigate(R.id.action_passengerFragment_to_nationalFormFragment, mBundle)
            }
        })
    }

    private fun setupPassengerItem() {
        listOfPassenger.clear()
        listObjectInformation.clear()

        for (i in 1..TEMP_PASSENGER_COUNTER){
            listOfPassenger.add("Passenger $i")
            listObjectInformation.add(TravelerModel("", 0))
        }
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

    companion object {
        const val TEMP_PASSENGER_COUNTER = 3
    }
}