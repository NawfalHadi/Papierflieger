package com.papierflieger.presentation.ui.admin.airplane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.databinding.FragmentAddAirplaneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAirplaneFragment : Fragment() {

    private lateinit var binding: FragmentAddAirplaneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAirplaneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}