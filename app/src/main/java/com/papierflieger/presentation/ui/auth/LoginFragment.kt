package com.papierflieger.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.papierflieger.R
import com.papierflieger.databinding.FragmentLoginBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.ui.admin.AdminActivity
import com.papierflieger.presentation.ui.home.HomeActivity
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val openFirstTime = false
        if (openFirstTime) findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        clickListener()
    }

    private fun clickListener() {
        binding.apply {
            linkSignup.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnSignIn.setOnClickListener {
                startActivity(Intent(activity, HomeActivity::class.java))
                activity?.finish()
            }

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (validationInput(email, password)) {
                    authViewModel.loginEmailPassword(email, password).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Empty -> {}
                            is Resource.Error -> {
                                val error = it.throwable?.message
                                if (error != null && error != "") {
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
                                    tilEtEmail.isErrorEnabled = false
                                    tilEtPassword.isErrorEnabled = false
                                }
                            }
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                authViewModel.saveLoginData(
                                    it.payload?.token.toString(),
                                    email,
                                    it.payload?.username.toString(),
                                    it.payload?.avatar.toString(),
                                    it.payload?.role.toString()
                                )
                                // Set Token Login
                                if (it.payload?.role.toString() == "Customer") {
                                    startActivity(Intent(activity, HomeActivity::class.java))
                                } else {
                                    startActivity(Intent(activity, AdminActivity::class.java))
                                }
                                activity?.finish()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validationInput(email: String, password: String): Boolean {
        var isFormValid = true
        binding.apply {
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
        }
        return isFormValid
    }

}