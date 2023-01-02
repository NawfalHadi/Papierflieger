package com.papierflieger.presentation.ui.home.payments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.data.network.response.ticket.TicketsDetail
import com.papierflieger.databinding.FragmentPaymentStatusBinding
import com.papierflieger.presentation.ui.home.history.DetailHistoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentMessageFragment : Fragment() {
    private lateinit var binding : FragmentPaymentStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentStatusBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val listOfFlight = arguments?.getParcelableArrayList<TicketsDetail>("FLIGHT_LIST")

        with(binding){
            btnHome.setOnClickListener {
                activity?.finish()
            }

            btnTicketDetail.setOnClickListener {
                val mBundle = Bundle()
//                mBundle.putParcelableArrayList("FLIGHT_LIST", listOfFlight)
                val mIntent = Intent(activity, DetailHistoryActivity::class.java)
                mIntent.putExtras(mBundle)

                startActivity(mIntent)
                activity?.finish()
            }
        }
    }
}