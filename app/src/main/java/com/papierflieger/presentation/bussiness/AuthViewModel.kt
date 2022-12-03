package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.data.repository.AuthenticationRepository
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
                _registerResponsed.postValue(responsed)
            }
        }
    }

    // fun registerGmail(){}

    // fun registerFacebook(){}

}