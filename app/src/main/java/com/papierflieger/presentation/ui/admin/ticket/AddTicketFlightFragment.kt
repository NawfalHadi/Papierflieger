package com.papierflieger.presentation.ui.admin.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.databinding.FragmentAddTicketFlightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketFlightFragment : Fragment() {

    private lateinit var binding : FragmentAddTicketFlightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTicketFlightBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}