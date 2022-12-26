package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.airplane.AirplaneResponse
import com.papierflieger.data.network.response.airplane.AirplanesResponse
import com.papierflieger.data.network.response.airport.AirportResponse
import com.papierflieger.data.network.response.airport.AirportsResponse
import com.papierflieger.data.repository.AirplaneRepository
import com.papierflieger.data.repository.AirportRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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