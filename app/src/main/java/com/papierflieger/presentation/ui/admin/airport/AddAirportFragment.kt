package com.papierflieger.presentation.ui.admin.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.papierflieger.R
import com.papierflieger.data.network.response.airport.Airport
import com.papierflieger.databinding.FragmentAddAirportBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAirportFragment : Fragment() {

    private lateinit var binding: FragmentAddAirportBinding
    private val adminViewModel : AdminViewModel by viewModels()
    private val airportViewModel : AirportsViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private var idAirport : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentAddAirportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNavigation()
        getEditData()
        clickListener()
    }

    private fun clickListener() {
        binding.apply {
            btnSave.setOnClickListener {
                saveData()
            }
        }
    }

    private fun saveData() {
        binding.apply {
            val airportName = etAirportName.text.toString().trim()
            val city = etCity.text.toString().trim()
            val cityCode = etCityCode.text.toString().trim()

            if (validationInput(airportName, city, cityCode)) {
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    if (idAirport != null) {
                        adminViewModel.updateAirport(idAirport!!, token,airportName, city, cityCode)
                    } else {
                        adminViewModel.createAirport(token, airportName, city, cityCode )
                    }
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
        }
    }

    private fun validationInput(airportName: String, city: String, cityCode: String): Boolean {
        var isFormValid = true
        binding.apply {
            if (airportName.isEmpty()) {
                isFormValid = false
                tilEtAirportName.isErrorEnabled = true
                tilEtAirportName.error = "Airport Name Empty"
            } else {
                tilEtAirportName.isErrorEnabled = false
            }

            if (city.isEmpty()) {
                isFormValid = false
                tilEtCity.isErrorEnabled = true
                tilEtCity.error = "City Empty"
            } else {
                tilEtCity.isErrorEnabled = false
            }

            if (cityCode.isEmpty()) {
                isFormValid = false
                tilEtCityCode.isErrorEnabled = true
                tilEtCityCode.error = "City Code Empty"
            } else {
                tilEtCityCode.isErrorEnabled = false
            }
        }
        return isFormValid
    }

    private fun getEditData() {
        idAirport = arguments?.getInt("id")
        if (idAirport != null) {
            airportViewModel.getAirportById(idAirport!!).observe(viewLifecycleOwner) {
                setEditData(it.payload!!.airport)
            }
        }
    }

    private fun setEditData(airport: Airport) {
        binding.apply {
            etAirportName.setText(airport.airportName)
            etCity.setText(airport.city)
            etCityCode.setText(airport.cityCode)
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}