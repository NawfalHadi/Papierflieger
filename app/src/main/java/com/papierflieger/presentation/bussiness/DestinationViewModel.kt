package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.DestinationsResponse
import com.papierflieger.data.repository.DestinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val destinationRepository : DestinationRepository
) : ViewModel(){

//    private val

//    init {
//
//    }
//
    fun getDestination() : LiveData<DestinationsResponse> {
        return destinationRepository.getDestination()
    }

}