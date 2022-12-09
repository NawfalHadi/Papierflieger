package com.papierflieger.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.papierflieger.R
import com.papierflieger.databinding.FragmentLoginBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

//    private fun snackbarMessage() {
//        authViewModel.snackbarMsg.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { msg ->
//                activity?.window?.decorView?.rootView?.let { rootView ->
//                    Snackbar.make(
//                        rootView,
//                        msg,
//                        Snackbar.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }

    private fun allNavigation() {
        binding.linkSignup.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        }
    }


}