package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.local.datastore.AccountDataStore
import com.papierflieger.data.network.response.user.User
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

    fun getProfileUser(token : String) : LiveData<Resource<User>>{
        return sessionRepo.getProfileUser(token)
    }

    // Data Store

    fun getToken() : LiveData<String> {
        return accountDS.getToken().asLiveData()
    }

    fun getName() : LiveData<String> {
        return accountDS.getNames().asLiveData()
    }

    fun getAvatar() : LiveData<String> {
        return accountDS.getAvatar().asLiveData()
    }

    fun getEmail() : LiveData<String> {
        return accountDS.getEmail().asLiveData()
    }

    fun getRole() : LiveData<String> {
        return accountDS.getRole().asLiveData()
    }

    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            accountDS.logout()
        }
    }

}