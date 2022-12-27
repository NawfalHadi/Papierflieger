package com.papierflieger.presentation.ui.admin.destination

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.papierflieger.R
import com.papierflieger.data.network.response.airport.Airport
import com.papierflieger.data.network.response.destination.Destination
import com.papierflieger.databinding.FragmentAddDestinationBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.DestinationViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.destinations.AdminDestinationPictureAdapter
import com.papierflieger.wrapper.CustomAdapter
import com.papierflieger.wrapper.Resource
import com.papierflieger.wrapper.convertAirport
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDestinationFragment : Fragment() {

    private lateinit var binding : FragmentAddDestinationBinding
    private val adminViewModel : AdminViewModel by viewModels()
    private val destinationViewModel : DestinationViewModel by viewModels()
    private val airportViewModel : AirportsViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private val adapterDestinationPicture : AdminDestinationPictureAdapter by lazy {
        AdminDestinationPictureAdapter()
    }

    private var idDestination : Int? = null
    private var uploadPhoto : String? = null
    private var airportId : Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentAddDestinationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNavigation()
        initList()
        getEditData()
        clickListener()
        observeData()
    }

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            galleryResult.launch("image/*")
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                checkingPermissions()
            }
            false
        } else {
            true
        }
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
//                val pickedPhoto: Bitmap = if (Build.VERSION.SDK_INT >= 28) {
//                    val source = ImageDecoder.createSource(requireActivity().contentResolver, result)
//                    ImageDecoder.decodeBitmap(source)
//                } else {
//                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, result)
//                }

                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()

                uploadPhoto = result.toString()
                binding.apply {
                    cvImageEmpty.visibility = View.VISIBLE
                    rvDestinationPicture.visibility = View.INVISIBLE
                    ivEmpty.load(result) {
                        placeholder(R.color.background_gray)
                    }
                }
            }
        }

    private fun observeData() {
        airportViewModel.getAirports().observe(viewLifecycleOwner) { airport ->
            when(airport) {
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.apply {
                        acAirport.setAdapter(CustomAdapter(requireContext(), airport.payload?.airports))
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickListener() {
        binding.apply {
            tvDestinationPictureAdd.setOnClickListener {
                checkingPermissions()
            }
            btnSave.setOnClickListener {
//                saveData()
            }
            acAirport.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as Airport
                acAirport.setText(convertAirport(selectedItem.airportName.toString(), selectedItem.cityCode.toString()))
                airportId = selectedItem.id!!
            }
        }
    }

    private fun saveData() {
        binding.apply {
            val name = etDestinationName.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val description = etDescription.text.toString().trim()

            if (validationInput(name, location, description)) {
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    if (idDestination != null) {
                        adminViewModel.updateDestination(idDestination!!, token, name, uploadPhoto!!, location, description, airportId)
                    } else {
                        adminViewModel.createDestination(token, name, uploadPhoto!!,  location, description, airportId)
                    }
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
        }
    }


    private fun validationInput(name: String, location: String, description: String): Boolean {
        var isFormValid = true
        binding.apply {
            if (name.isEmpty()) {
                isFormValid = false
                tilEtDestinationName.isErrorEnabled = true
                tilEtDestinationName.error = "Destination Name Empty"
            } else {
                tilEtDestinationName.isErrorEnabled = false
            }

            if (airportId <= 0) {
                isFormValid = false
                tilEtAirport.isErrorEnabled = true
                tilEtAirport.error = "Airport Empty"
            } else {
                tilEtAirport.isErrorEnabled = false
            }

            if (location.isEmpty()) {
                isFormValid = false
                tilEtLocation.isErrorEnabled = true
                tilEtLocation.error = "Location Empty"
            } else {
                tilEtLocation.isErrorEnabled = false
            }

            if (description.isEmpty()) {
                isFormValid = false
                tilEtDescription.isErrorEnabled = true
                tilEtDescription.error = "Description Empty"
            } else {
                tilEtDescription.isErrorEnabled = false
            }

            if (uploadPhoto.isNullOrEmpty()) {
                isFormValid = false
                Toast.makeText(context, "Destination Picture Empty ", Toast.LENGTH_SHORT).show()
            }

        }
        return isFormValid
    }

    private fun getEditData() {
        idDestination = arguments?.getInt("id")
        if (idDestination != null) {
            destinationViewModel.getDestinationById(idDestination!!).observe(viewLifecycleOwner) {
                setEditData(it.destination)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setEditData(destination: Destination) {
        binding.apply {
            etDestinationName.setText(destination.name)
            etLocation.setText(destination.location)
            etDescription.setText(destination.description)
            cvImageEmpty.visibility = View.INVISIBLE
            if (destination.image.isNullOrEmpty().not()) {
                adapterDestinationPicture.setItem(destination.image!!)
            }
            acAirport.setText(
                destination.airport?.airportName + " (" + destination.airport?.cityCode.toString() + ")"
                , false)
            airportId = destination.airport?.id!!
        }
    }

    private fun initList() {
        binding.rvDestinationPicture.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = adapterDestinationPicture
        }
    }

    private fun allNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}