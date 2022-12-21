package com.papierflieger.presentation.ui.admin.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminDashboardBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardFragment : Fragment() {

    private lateinit var binding : FragmentAdminDashboardBinding
    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingSession()
        bindingTitle()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_dashboard)
    }

    private fun bindingSession() {
        authViewModel.getNames().observe(viewLifecycleOwner){
            binding.tvName.text = it
        }

        authViewModel.getEmail().observe(viewLifecycleOwner){
            binding.tvEmail.text = it
        }

        authViewModel.getAvatar().observe(viewLifecycleOwner){
            binding.ivAvatar.load(it){
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_person_circle)
            }
        }
    }
}