package com.papierflieger.presentation.bussiness

import androidx.lifecycle.*
import com.papierflieger.data.local.datastore.AccountDataStore
import com.papierflieger.data.network.response.auth.LoginResponse
import com.papierflieger.data.network.response.auth.RegisterResponse
import com.papierflieger.data.repository.AuthRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val AuthRepository : AuthRepository,
    private val accountDS : AccountDataStore
) : ViewModel() {

    fun loginEmailPassword(
        email: String,
        password: String
    ): LiveData<Resource<LoginResponse>> {
        return AuthRepository.login(
            email,
            password
        )
    }

    fun registerEmailPassword(
        username: String,
        fullName: String,
        email: String,
        password: String
    ): LiveData<Resource<RegisterResponse>> {
        return AuthRepository.register(
            username,
            fullName,
            email,
            password
        )
    }

    // Data Store
    fun saveLoginData(token: String, email: String, name: String, image: String, role: String){
        viewModelScope.launch(Dispatchers.IO){
            accountDS.loginSuccess(token, email, name, image, role)
        }
    }

}