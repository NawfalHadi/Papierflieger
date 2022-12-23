package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.local.datastore.DatasyncDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatasyncViewModel @Inject constructor(
    private val datasyncDS: DatasyncDataStore
) : ViewModel() {

    //Airports
    fun airportSynced() {
        viewModelScope.launch(Dispatchers.IO) {
            datasyncDS.syncAirport()
        }
    }

    fun isAirportSync() : LiveData<Boolean>{
        return datasyncDS.isAirportSynced().asLiveData()
    }

}