package com.papierflieger.presentation.bussiness

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.data.repository.AuthenticationRepository
import com.papierflieger.wrapper.Event
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val basicAuthRepo : AuthenticationRepository
) : ViewModel() {

    private val _registerResponsed = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponsed : LiveData<Resource<RegisterResponse>> = _registerResponsed

    private val _snackbarMsg = MutableLiveData<Event<String>>()
    val snackbarMsg: LiveData<Event<String>> = _snackbarMsg

    fun registerEmailPassword(
        username: String,
        fullname: String,
        email: String,
        password: String
    ){
        _registerResponsed.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val responsed = basicAuthRepo.register(
                username = username,
                fullname = fullname,
                email = email,
                password = password
            )
            viewModelScope.launch(Dispatchers.Main){
                _snackbarMsg.value = Event(responsed.payload?.message.toString())
                _registerResponsed.postValue(responsed)
            }
        }
    }

    fun snackbarTrigger(){
        _snackbarMsg.value = snackbarMsg.value
    }

    // fun registerGmail(){}

    // fun registerFacebook(){}

}