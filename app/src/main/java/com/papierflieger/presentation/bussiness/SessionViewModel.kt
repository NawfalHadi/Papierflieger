package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.local.datastore.AccountDataStore
import com.papierflieger.data.network.response.Profile
import com.papierflieger.data.repository.SessionRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepo : SessionRepository,
    private val accountDS : AccountDataStore
) : ViewModel() {

    fun getProfileUser(token : String) : LiveData<Resource<Profile>>{
        return sessionRepo.getProfileUser(token)
    }

    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            accountDS.logout()
        }
    }

}