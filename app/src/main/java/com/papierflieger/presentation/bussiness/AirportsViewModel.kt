package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.airport.AirportResponse
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

    val listOfflineAirports = MutableLiveData<Resource<List<AirportEntity>>>()

    fun syncAirportsData(status: Boolean) : LiveData<Resource<AirportsResponse>>{
        return airportRepository.syncAirportData(status)
    }

    fun addAirports(airport: AirportEntity){
        viewModelScope.launch(Dispatchers.IO) {
            airportRepository.offlineAirportData(airport)
        }
    }

    fun getSavedAirports() {
        viewModelScope.launch(Dispatchers.IO) {
            val airports = airportRepository.getOfflineAirports()
            viewModelScope.launch(Dispatchers.Main) {
                listOfflineAirports.postValue(airports)
            }
        }
    }

    fun selectAirport(id: Int){
        airportRepository.selectAirport(id)
    }

    // Admin

    fun getAirports() : LiveData<Resource<AirportsResponse>>{
        return airportRepository.getAirports()
    }

    fun getAirportById(idAirport: Int) : LiveData<Resource<AirportResponse>>{
        return airportRepository.getAirportById(idAirport)
    }


}