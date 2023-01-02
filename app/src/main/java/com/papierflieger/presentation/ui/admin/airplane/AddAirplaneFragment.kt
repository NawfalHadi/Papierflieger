package com.papierflieger.presentation.ui.admin.airplane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.papierflieger.R
import com.papierflieger.data.network.response.airplane.DataAirplane
import com.papierflieger.databinding.FragmentAddAirplaneBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.AirplaneViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAirplaneFragment : Fragment() {

    private lateinit var binding: FragmentAddAirplaneBinding
    private val adminViewModel : AdminViewModel by viewModels()
    private val airplaneViewModel : AirplaneViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private var idAirplane : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentAddAirplaneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNavigation()
        getEditData()
        observeData()
        clickListener()
    }

    private fun observeData() {
        val options = arrayOf("Economy", "Business")
        (binding.acClass as MaterialAutoCompleteTextView).setSimpleItems(options)
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
            val airplaneName = etAirplaneName.text.toString().trim()
            val airplaneCode = etAirplaneCode.text.toString().trim()
            val category = acClass.text.toString().trim()

            if (validationInput(airplaneName, airplaneCode, category)) {
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    if (idAirplane != null) {
                        adminViewModel.updateAirplane(idAirplane!!, token, airplaneName, airplaneCode, category)
                    } else {
                        adminViewModel.createAirplane(token, airplaneName, airplaneCode, category)
                    }
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
        }
    }

    private fun validationInput(airplaneName: String, airplaneCode: String, category: String): Boolean {
        var isFormValid = true
        binding.apply {
            if (airplaneName.isEmpty()) {
                isFormValid = false
                tilEtAirplaneName.isErrorEnabled = true
                tilEtAirplaneName.error = "Airplane Name Empty"
            } else {
                tilEtAirplaneName.isErrorEnabled = false
            }

            if (airplaneCode.isEmpty()) {
                isFormValid = false
                tilEtAirplaneCode.isErrorEnabled = true
                tilEtAirplaneCode.error = "Airplane Code Empty"
            } else {
                tilEtAirplaneCode.isErrorEnabled = false
            }

            if (category.isEmpty()) {
                isFormValid = false
                tilEtClass.isErrorEnabled = true
                tilEtClass.error = "Class Empty"
            } else {
                tilEtClass.isErrorEnabled = false
            }
        }
        return isFormValid
    }

    private fun getEditData() {
        idAirplane = arguments?.getInt("id")
        if (idAirplane != null) {
            airplaneViewModel.getAirplaneById(idAirplane!!).observe(viewLifecycleOwner) {
                setEditData(it.payload!!.flight)
            }
        }
    }

    private fun setEditData(airplane: DataAirplane) {
        binding.apply {
            etAirplaneName.setText(airplane.airplaneName)
            etAirplaneCode.setText(airplane.airplaneCode)
            acClass.setText(airplane.category, false)
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}