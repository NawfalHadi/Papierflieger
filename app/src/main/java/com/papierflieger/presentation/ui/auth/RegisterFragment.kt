package com.papierflieger.presentation.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.papierflieger.R
import com.papierflieger.databinding.FragmentRegisterBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allNavigation()
        with(binding){
            btnSignup.setOnClickListener {
                authViewModel.registerEmailPassword(
                    username = etUsername.text.toString(),
                    fullname = etFullname.text.toString(),
                    email = etEmail.text.toString(),
                    password = etConfirmPassword.text.toString()
                )
            }
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.linkHaveaccount.setOnClickListener {
            findNavController().popBackStack()
        }

        authViewModel.registerResponsed.observe(viewLifecycleOwner) {
            when(it){
                 is Resource.Success -> {
                     val mBundle = Bundle()
                     mBundle.putString(AuthActivity.MESSAGE_KEY, it.message)
                     findNavController().navigate(R.id.action_registerFragment_to_loginFragment, mBundle)
                 }
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }

    }

    private fun snackbarMessage() {
        authViewModel.snackbarMsg.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { msg ->
                activity?.window?.decorView?.rootView?.let { rootView ->
                    Snackbar.make(
                        rootView,
                        msg,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}