package com.papierflieger.presentation.ui.home.profiles.settings.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.papierflieger.data.network.response.user.UpdateUserResponse
import com.papierflieger.data.network.response.user.User
import com.papierflieger.databinding.FragmentPersonalAddressBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.bussiness.UserViewModel
import com.papierflieger.presentation.ui.home.profiles.settings.AccountInformationActivity
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressInformationFragment : Fragment() {

    private lateinit var binding : FragmentPersonalAddressBinding
    private val userViewModel : UserViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalAddressBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile = arguments?.getParcelable<User>(AccountInformationActivity.PERSONAL_INFORMATION)
        bindingData(profile!!)

        with(binding){
            btnSave.setOnClickListener {
                if (isValueValid()) {
                    userViewModel.updateAddressInformation(
                        token,
                        etCountry.text.toString(),
                        etProvince.text.toString(),
                        etRegency.text.toString()
                    ).observe(viewLifecycleOwner){
                        responseAction(it)
                    }
                }
            }
        }
    }

    private fun responseAction(responsed: Resource<UpdateUserResponse>?) {
        when (responsed) {
            is Resource.Success -> {
                findNavController().popBackStack()
            }
            is Resource.Empty -> TODO()
            is Resource.Error -> TODO()
            is Resource.Loading -> TODO()
            null -> TODO()
        }
    }

    private fun bindingData(profile: User) {
        with(binding){
            etCountry.setText(profile.country)
            etProvince.setText(profile.province)
            etRegency.setText(profile.regency)
        }

        sessionViewModel.getToken().observe(viewLifecycleOwner){
            token = it
        }
    }

    private fun isValueValid(): Boolean {
        with(binding) {
            val country = etCountry.text.toString()
            val province = etProvince.text.toString()
            val regency = etRegency.text.toString()
        }

        /***
         * Edit Text Must Not NULL Checker
         */

        return true
    }
}