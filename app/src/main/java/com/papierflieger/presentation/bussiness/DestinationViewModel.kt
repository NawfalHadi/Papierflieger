package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.*
import com.papierflieger.data.repository.DestinationRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val destinationRepository : DestinationRepository
) : ViewModel(){

    fun getDestination() : LiveData<DestinationsResponse> {
        return destinationRepository.getDestination()
    }

    fun getDestinationById(id: Int) : LiveData<DestinationResponse> {
        return destinationRepository.getDestinationById(id)
    }

}