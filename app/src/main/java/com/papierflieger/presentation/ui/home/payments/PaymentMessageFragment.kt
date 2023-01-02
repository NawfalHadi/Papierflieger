package com.papierflieger.presentation.ui.home.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.databinding.FragmentPaymentStatusBinding
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

        with(binding){
            btnHome.setOnClickListener {
                activity?.finish()
            }
        }
    }
}