package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.airplane.AirplaneResponse
import com.papierflieger.data.network.response.airplane.AirplanesResponse
import com.papierflieger.data.repository.AirplaneRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AirplaneViewModel @Inject constructor(
    private val airplaneRepository: AirplaneRepository
) : ViewModel() {

    fun getAirplanes() : LiveData<Resource<AirplanesResponse>>{
        return airplaneRepository.getAirplanes()
    }

    fun getAirplaneById(idAirplane: Int) : LiveData<Resource<AirplaneResponse>>{
        return airplaneRepository.getAirplaneById(idAirplane)
    }

}