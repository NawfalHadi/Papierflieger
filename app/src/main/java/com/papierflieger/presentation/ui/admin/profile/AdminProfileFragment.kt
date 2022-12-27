package com.papierflieger.presentation.ui.admin.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminProfileBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminProfileFragment : Fragment() {

    private lateinit var binding : FragmentAdminProfileBinding
    private val authViewModel : AuthViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingTitle()
        bindingData()
        bindingLogout()
    }

    private fun bindingData() {
        authViewModel.getAvatar().observe(viewLifecycleOwner) {
            binding.ivAvatar.load(it) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_person_circle)
            }
        }
        authViewModel.getNames().observe(viewLifecycleOwner) {
            binding.tvName.text = it
        }
        authViewModel.getEmail().observe(viewLifecycleOwner) {
            binding.tvEmail.text = it
        }
    }

    private fun bindingLogout() {
        binding.cvLogout.setOnClickListener {
            sessionViewModel.logout()
            findNavController().navigate(R.id.action_adminProfileFragment_to_authActivity)
            activity?.finish()
        }
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_profile)
    }

}