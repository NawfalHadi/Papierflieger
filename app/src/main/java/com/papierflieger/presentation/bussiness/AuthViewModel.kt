package com.papierflieger.presentation.bussiness

import android.util.Log
import androidx.lifecycle.*
import com.papierflieger.data.local.datastore.AccountDataStore
import com.papierflieger.data.network.response.LoginResponse
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
    private val basicAuthRepo : AuthenticationRepository,
    private val accountDS : AccountDataStore
) : ViewModel() {

    private val _registerResponsed = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponsed : LiveData<Resource<RegisterResponse>> = _registerResponsed

    private val _loginResponsed = MutableLiveData<Resource<LoginResponse>>()
    val loginResponsed : LiveData<Resource<LoginResponse>> = _loginResponsed

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

    fun loginEmailPassword(
        email: String,
        password: String
    ){
        _loginResponsed.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val responsed = basicAuthRepo.login(
                email = email, password = password
            )
            viewModelScope.launch(Dispatchers.Main){
                _snackbarMsg.value = Event(responsed.payload?.message.toString())
                _loginResponsed.postValue(responsed)
            }
        }
    }

    // Data Store
    fun loginSuccuess(token: String, email: String){
        viewModelScope.launch(Dispatchers.IO){
            accountDS.loginSuccess(token, email)
        }
    }

    fun getToken() : LiveData<String> {
        return accountDS.getToken().asLiveData()
    }

    fun snackbarTrigger(){
        _snackbarMsg.value = snackbarMsg.value
    }

    // fun registerGmail(){}

    // fun registerFacebook(){}

}