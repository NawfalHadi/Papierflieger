package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.user.UpdateUserResponse
import com.papierflieger.data.repository.UserRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo : UserRepository
) : ViewModel() {

    fun updatePersonalInformation(
        token : String , title : String, username : String, fullname : String, birthdate : String, nationality : String
    ): LiveData<Resource<UpdateUserResponse>> {
        return userRepo.updatePersonalInformation(
            token, title, username, fullname, birthdate, nationality
        )
    }

    fun updateAddressInformation(
        token : String, country: String, province: String, regency: String
    ): LiveData<Resource<UpdateUserResponse>> {
        return userRepo.updateAddressInformation(
            token, country, province, regency
        )
    }

}