package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.airport.AirportsResponse
import com.papierflieger.data.repository.AirportRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportsViewModel @Inject constructor(
    private val airportRepository: AirportRepository
) : ViewModel() {

    fun syncAirportsData(status: Boolean) : LiveData<Resource<AirportsResponse>>{
        return airportRepository.syncAirportData(status)
    }

    fun addAirports(airport: AirportEntity){
        viewModelScope.launch(Dispatchers.IO) {
            airportRepository.offlineAirportData(airport)
        }
    }

    fun getAirport(id: Int){
        airportRepository.selectAirport(id)
    }
}