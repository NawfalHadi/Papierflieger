package com.papierflieger.presentation.ui.home.profiles.settings.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.papierflieger.data.network.response.user.Profile
import com.papierflieger.data.network.response.user.UpdateUserResponse
import com.papierflieger.databinding.FragmentPersonalInformationBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.bussiness.UserViewModel
import com.papierflieger.presentation.ui.home.profiles.settings.AccountInformationActivity
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInformationFragment : Fragment() {

    private lateinit var binding : FragmentPersonalInformationBinding
    private val userViewModel : UserViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile = arguments?.getParcelable<Profile>(AccountInformationActivity.PERSONAL_INFORMATION)
        bindingData(profile!!)

        with(binding){
            btnSave.setOnClickListener {
                if (isValueValid()) {
                    userViewModel.updatePersonalInformation(
                        token,
                        etTitle.text.toString(),
                        etFullName.text.toString(),
                        etUsername.text.toString(),
                        etBirthdate.text.toString(),
                        etNationality.text.toString()
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

    private fun bindingData(profile: Profile) {
        with(binding){
            etTitle.setText(profile.title)
            etFullName.setText(profile.fullName)
            etUsername.setText(profile.username)
            etBirthdate.setText(profile.birthdate)
            etNationality.setText(profile.nationality)
        }

        sessionViewModel.getToken().observe(viewLifecycleOwner){
            token = it
        }
    }

    private fun isValueValid(): Boolean {
        with(binding) {
            val title = etTitle.text.toString()
            val fullName = etFullName.text.toString()
            val username = etUsername.text.toString()
            val birthdate = etBirthdate.text.toString()
            val nationality = etNationality.text.toString()
        }

        /***
         * Edit Text Must Not NULL Checker
         */

        return true
    }


}