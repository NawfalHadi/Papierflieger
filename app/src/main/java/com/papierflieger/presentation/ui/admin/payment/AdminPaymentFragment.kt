package com.papierflieger.presentation.ui.admin.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminDestinationBinding
import com.papierflieger.databinding.FragmentAdminPaymentBinding

class AdminPaymentFragment : Fragment() {

    private lateinit var binding : FragmentAdminPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingTitle()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_payment)
    }

}