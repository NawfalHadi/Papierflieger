package com.papierflieger.presentation.ui.admin.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.databinding.FragmentAddTicketTransitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketTransitFragment : Fragment() {

    private lateinit var binding: FragmentAddTicketTransitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTicketTransitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}