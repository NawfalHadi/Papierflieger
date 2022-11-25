package com.papierflieger.presentation.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.papierflieger.R
import com.papierflieger.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allNavigation()
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.linkHaveaccount.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}