package com.papierflieger.presentation.ui.admin.airport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAddAirportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAirportFragment : Fragment() {

    private lateinit var binding: FragmentAddAirportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAirportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}