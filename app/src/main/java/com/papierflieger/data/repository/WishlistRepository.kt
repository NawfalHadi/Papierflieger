package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.user.UpdateUserResponse
import com.papierflieger.data.network.response.wishlist.CreateWishlistResponse
import com.papierflieger.data.network.response.wishlist.WishlistResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistRepository(
  private val apiService : ApiService
) {

    private var wishlistResponse : MutableLiveData<Resource<WishlistResponse>> = MutableLiveData()
    private var createWishlistResponse : MutableLiveData<Resource<CreateWishlistResponse>> = MutableLiveData()
    private var deleteWishlistResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    fun getWishlist(
        token: String,
    ) : LiveData<Resource<WishlistResponse>> {
        apiService.getWishlist(token).enqueue(object : Callback<WishlistResponse>{
            override fun onResponse(
                call: Call<WishlistResponse>,
                response: Response<WishlistResponse>
            ) {
                if (response.isSuccessful){
                    wishlistResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<WishlistResponse>, t: Throwable) {
                wishlistResponse.postValue(Resource.Error(t))
            }
        })
        return wishlistResponse
    }

    fun createWishlist(
        token: String,
        id: Int,
    ) : LiveData<Resource<CreateWishlistResponse>> {
        apiService.createWishlist(token, id).enqueue(object : Callback<CreateWishlistResponse>{
            override fun onResponse(
                call: Call<CreateWishlistResponse>,
                response: Response<CreateWishlistResponse>
            ) {
                if (response.isSuccessful){
                    createWishlistResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<CreateWishlistResponse>, t: Throwable) {
                createWishlistResponse.postValue(Resource.Error(t))
            }
        })
        return createWishlistResponse
    }

    fun deleteWishlist(
        token: String,
        id: Int,
    ) : LiveData<Resource<ChangeDataResponse>> {
        apiService.deleteWishlist(token, id).enqueue(object : Callback<ChangeDataResponse>{
            override fun onResponse(
                call: Call<ChangeDataResponse>,
                response: Response<ChangeDataResponse>
            ) {
                if (response.isSuccessful){
                    deleteWishlistResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                deleteWishlistResponse.postValue(Resource.Error(t))
            }
        })
        return deleteWishlistResponse
    }

}