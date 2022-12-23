package com.papierflieger.presentation.ui.admin.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAddTicketDetailBinding
import com.papierflieger.databinding.FragmentAddTicketFlightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketDetailFragment : Fragment() {

    private lateinit var binding: FragmentAddTicketDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTicketDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}