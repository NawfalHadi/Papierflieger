package com.papierflieger.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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

        // start - temp
        val openFirstTime = false
        // end - temp

        if (openFirstTime){
            findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }

        allNavigation()
        allMessages()
    }

    private fun allMessages() {
        snackbarMessage()

        val msg = arguments?.getString(AuthActivity.MESSAGE_KEY) ?: ""
        if (msg.isNotEmpty()){
            activity?.window?.decorView?.rootView?.let { rootView ->
                    Snackbar.make(
                        rootView,
                        msg,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
        }

        authViewModel.loginResponsed.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    authViewModel.loginSuccuess(
                        it.payload?.token.toString(),
                        binding.etEmail.text.toString(),
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
                is Resource.Empty -> {}
                is Resource.Error -> {
                    Toast.makeText(this.context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
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

    private fun allNavigation() {
        with(binding) {
            linkSignup.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnSignin.setOnClickListener {
                startActivity(Intent(activity, HomeActivity::class.java))
                activity?.finish()
            }

            // Login

            btnSignin.setOnClickListener{
                authViewModel.loginEmailPassword(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString()
                )
            }
        }
    }


}