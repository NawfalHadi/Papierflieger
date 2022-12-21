package com.papierflieger.presentation.ui.admin.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminAirplaneBinding
import com.papierflieger.databinding.FragmentAdminAirportBinding

class AdminAirportFragment : Fragment() {

    private lateinit var binding : FragmentAdminAirportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminAirportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingTitle()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_airport)
    }

}