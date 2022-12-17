package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.Profile
import com.papierflieger.data.repository.SessionRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepo : SessionRepository
) : ViewModel() {

    fun getProfileUser(token : String) : LiveData<Resource<Profile>>{
        return sessionRepo.getProfileUser(token)
    }

}