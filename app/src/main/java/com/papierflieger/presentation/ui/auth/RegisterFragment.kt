package com.papierflieger.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.papierflieger.databinding.FragmentRegisterBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        binding.apply {
            binding.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            binding.linkHaveaccount.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSignup.setOnClickListener {
                val username = etUsername.text.toString()
                val fullName = etFullName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()

                if (validationInput(username, fullName, email, password, confirmPassword)) {
                    authViewModel.registerEmailPassword(
                        username,
                        fullName,
                        email,
                        password
                    ).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Empty -> {}
                            is Resource.Error -> {
                                val error = it.throwable?.message
                                if (error != null && error != "") {
                                    if (error.contains("Username")) {
                                        tilEtUsername.isErrorEnabled = true
                                        tilEtUsername.error = error
                                    } else {
                                        tilEtUsername.isErrorEnabled = false
                                    }

                                    if (error.contains("Email")) {
                                        tilEtEmail.isErrorEnabled = true
                                        tilEtEmail.error = error
                                    } else {
                                        tilEtEmail.isErrorEnabled = false
                                    }

                                    if(error.contains("Password")) {
                                        tilEtPassword.isErrorEnabled = true
                                        tilEtPassword.error = error
                                    } else {
                                        tilEtPassword.isErrorEnabled = false
                                    }
                                } else {
                                    tilEtUsername.isErrorEnabled = false
                                    tilEtEmail.isErrorEnabled = false
                                    tilEtPassword.isErrorEnabled = false
                                }
                            }
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                tilEtConfirmPassword.isErrorEnabled = false
                                Toast.makeText(context, it.payload?.message, Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun validationInput(
        username: String,
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isFormValid = true
        binding.apply {
            if (username.isEmpty()) {
                isFormValid = false
                tilEtUsername.isErrorEnabled = true
                tilEtUsername.error = "Username Empty"
            } else {
                tilEtUsername.isErrorEnabled = false
            }

            if (fullName.isEmpty()) {
                isFormValid = false
                tilEtFullName.isErrorEnabled = true
                tilEtFullName.error = "Full Name Empty"
            } else {
                tilEtFullName.isErrorEnabled = false
            }

            if (email.isEmpty()) {
                isFormValid = false
                tilEtEmail.isErrorEnabled = true
                tilEtEmail.error = "Email Empty"
            } else {
                tilEtEmail.isErrorEnabled = false
            }

            if (password.isEmpty()) {
                isFormValid = false
                tilEtPassword.isErrorEnabled = true
                tilEtPassword.error = "Password Empty"
            } else {
                tilEtPassword.isErrorEnabled = false
            }

            if (password.length < 8) {
                isFormValid = false
                tilEtPassword.isErrorEnabled = true
                tilEtPassword.error = "Password Must be Longer than 8 Characters"
            } else {
                tilEtPassword.isErrorEnabled = false
            }

            if (password != confirmPassword) {
                isFormValid = false
                tilEtConfirmPassword.isErrorEnabled = true
                tilEtConfirmPassword.error = "password and Confirm Password are not The Same"
            } else {
                tilEtConfirmPassword.isErrorEnabled = false
            }
        }
        return isFormValid
    }

}