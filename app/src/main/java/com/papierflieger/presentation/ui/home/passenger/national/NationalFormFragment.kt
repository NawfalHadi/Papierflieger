package com.papierflieger.presentation.ui.home.passenger.national

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.papierflieger.data.local.model.PassengersModel
import com.papierflieger.databinding.FragmentTravelerDetailBinding
import com.papierflieger.presentation.ui.home.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NationalFormFragment : Fragment() {

    private lateinit var binding : FragmentTravelerDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTravelerDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerId = arguments?.getInt(SearchActivity.PASSENGER_COUNTER_KEY)
        val customerInformation = arguments?.getParcelable<PassengersModel>(SearchActivity.PASSENGER_KEY)

        bindingInformation(customerInformation)

        with(binding){
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                val passengerNames = etFullName.text.toString()
                val nationality = etNationality.text.toString()
                val identityCard = etIdentityCard.text.toString()
                val birthdate = etDateBirth.text.toString()

                findNavController().previousBackStackEntry?.savedStateHandle?.set(SearchActivity.PASSENGER_COUNTER_KEY, customerId)
                findNavController().previousBackStackEntry?.savedStateHandle?.set(SearchActivity.PASSENGER_KEY, PassengersModel(
                    passengerNames = passengerNames,
                    nationality = nationality,
                    nik = identityCard,
                    birthDate = birthdate
                ))
                findNavController().popBackStack()
            }
        }
    }

    private fun bindingInformation(customerInformation: PassengersModel?) {
        with(binding){
            etFullName.setText(customerInformation?.passengerNames)
        }
    }

}