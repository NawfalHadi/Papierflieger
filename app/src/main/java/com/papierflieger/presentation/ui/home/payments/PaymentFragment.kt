package com.papierflieger.presentation.ui.home.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.data.network.response.orders.OrderResponse
import com.papierflieger.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding : FragmentPaymentBinding
    private lateinit var orderResponse: OrderResponse

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

        orderResponse = arguments?.getParcelable<OrderResponse>(ORDER_RESPONSE_KEY)!!
    }

    companion object {
        const val ORDER_RESPONSE_KEY = "ORDER_RESPONSE_KEY"
    }
}