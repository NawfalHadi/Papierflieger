package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.airport.AirportsResponse
import com.papierflieger.data.repository.AirportRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AirportsViewModel @Inject constructor(
    private val airportRepository: AirportRepository
) : ViewModel() {

    fun syncAirportsData(status: Boolean) : LiveData<Resource<AirportsResponse>>{
        return airportRepository.syncAirportData(status)
    }
}