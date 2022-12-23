package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.*
import com.papierflieger.data.repository.AdminRepository
import com.papierflieger.data.repository.DestinationRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel(){

    fun createDestination(
        token: String,
        name: String,
        images: String,
        location: String,
        description: String,
        airportId: Int
    ): LiveData<CreateDestinationResponse> {
        return adminRepository.createDestination(
            token, name, images, location, description, airportId
        )
    }

    fun updateDestination(
        idDestination: Int,
        token: String,
        name: String,
        images: List<String>,
        location: String,
        description: String,
        airportId: Int
    ): LiveData<Resource<UpdateDestinationResponse>> {
        return adminRepository.updateDestination(
            idDestination, token, name, images, location, description, airportId
        )
    }

}