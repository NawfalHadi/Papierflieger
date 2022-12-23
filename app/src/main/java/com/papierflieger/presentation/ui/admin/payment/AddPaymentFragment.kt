package com.papierflieger.presentation.ui.admin.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAddPaymentBinding
import com.papierflieger.databinding.FragmentAdminProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPaymentFragment : Fragment() {

    private lateinit var binding : FragmentAddPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}