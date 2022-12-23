package com.papierflieger.presentation.ui.admin.destination

import android.Manifest
import android.R.layout
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.papierflieger.data.network.response.Destination
import com.papierflieger.databinding.FragmentAddDestinationBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.DestinationViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.destinations.AdminDestinationPictureAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDestinationFragment : Fragment() {

    private lateinit var binding : FragmentAddDestinationBinding
    private val adminViewModel : AdminViewModel by viewModels()
    private val destinationViewModel : DestinationViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private val adapterDestinationPicture : AdminDestinationPictureAdapter by lazy {
        AdminDestinationPictureAdapter()
    }

    private var idDestination : Int? = null

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
                binding.cvImageEmpty.visibility = View.INVISIBLE
                adapterDestinationPicture.addItem(result)
            }
        }

    private fun observeData() {
        binding.apply {
            val options = arrayOf("Option 1", "Option 2", "Option 3")
            acAirport.setAdapter(ArrayAdapter(requireContext(), layout.simple_list_item_1, options))
        }
    }

    private fun clickListener() {
        binding.apply {
            tvDestinationPictureAdd.setOnClickListener {
                checkingPermissions()
            }
        }
    }

    private fun getEditData() {
        idDestination = arguments?.getInt("id")
        if (idDestination != null) {
            destinationViewModel.getDestinationById(idDestination!!).observe(viewLifecycleOwner) {
                setEditData(it.destination)
            }
        }
    }

    private fun setEditData(destination: Destination) {
        binding.apply {
            etDestinationName.setText(destination.name)
            etLocation.setText(destination.location)
            etDescription.setText(destination.description)
            cvImageEmpty.visibility = View.INVISIBLE
            adapterDestinationPicture.setItem(destination.image!!)
            acAirport.setText(destination.airportId.toString())
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