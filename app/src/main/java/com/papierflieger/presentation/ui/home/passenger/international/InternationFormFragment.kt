package com.papierflieger.presentation.ui.home.passenger.international

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.papierflieger.data.local.model.PassengersModel
import com.papierflieger.databinding.FragmentTravelerDetailInternationalBinding
import com.papierflieger.presentation.ui.home.search.SearchActivity
import com.papierflieger.wrapper.toRequestDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InternationFormFragment : Fragment() {
    private lateinit var binding : FragmentTravelerDetailInternationalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTravelerDetailInternationalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerId = arguments?.getInt(SearchActivity.PASSENGER_COUNTER_KEY)
        val customerInformation = arguments?.getParcelable<PassengersModel>(SearchActivity.PASSENGER_KEY)

        datePicking()
        dateValueChanging()
        bindingInformation(customerInformation)

        with(binding){
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                setInformation(customerId)
            }
        }
    }

    private fun dateValueChanging() {
        with(binding){
            birthdatePicker.addOnPositiveButtonClickListener {
                etDateBirth.setText(it.toRequestDate())
            }

            expiredDatePicker.addOnPositiveButtonClickListener {
                etExpirationDate.setText(it.toRequestDate())
            }
        }
    }

    private fun datePicking() {
        with(binding){
            etDateBirth.setOnClickListener {
                birthdatePicker.show(
                    childFragmentManager,
                    "date_picker"
                )
            }

            etExpirationDate.setOnClickListener {
                expiredDatePicker.show(
                    childFragmentManager,
                    "date_picker"
                )
            }
        }
    }

    private fun setInformation(customerId: Int?) {
        with(binding){
            val fullname = etFullName.text.toString()
            val passport = etPassport.text.toString()
            val datebirth = etDateBirth.text.toString()
            val nationality = etNationality.text.toString()
            val issuingCountry = etIssuingCountry.text.toString()
            val expired = etExpirationDate.text.toString()

            findNavController().previousBackStackEntry?.savedStateHandle?.set(SearchActivity.PASSENGER_COUNTER_KEY, customerId)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(SearchActivity.PASSENGER_KEY, PassengersModel(
                fullname, datebirth, nationality, passport, issuingCountry, expired
            ))
            findNavController().popBackStack()
        }
    }

    private fun bindingInformation(customerInformation: PassengersModel?) {
        with(binding){
            etFullName.setText(customerInformation?.passengerNames)
            etPassport.setText(customerInformation?.passportNumber)
            etDateBirth.setText(customerInformation?.birthDate)
            etNationality.setText(customerInformation?.nationality)
            etIssuingCountry.setText(customerInformation?.issuingCountry)
            etExpirationDate.setText(customerInformation?.expired)
        }
    }
}

private val birthdatePicker =
    MaterialDatePicker.Builder.datePicker()
        .setTitleText("BirthDate")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()

private val expiredDatePicker =
    MaterialDatePicker.Builder.datePicker()
        .setTitleText("Expired Date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(
            CalendarConstraints.Builder()
                .setStart(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .setValidator(DateValidatorPointForward.now())
                .build()
        )
        .build()