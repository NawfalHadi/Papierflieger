package com.papierflieger.presentation.ui.home.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.databinding.FragmentTransactionFlightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerFragment : Fragment(){

    private lateinit var binding : FragmentTransactionFlightBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionFlightBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}