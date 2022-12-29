package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.wishlist.CreateWishlistResponse
import com.papierflieger.data.network.response.wishlist.WishlistResponse
import com.papierflieger.data.repository.WishlistRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository : WishlistRepository
) : ViewModel() {

    fun getWishlist(
        token: String
    ): LiveData<Resource<WishlistResponse>> {
        return wishlistRepository.getWishlist(
            token
        )
    }

    fun createWishlist(
        token: String,
        id: Int
    ): LiveData<Resource<CreateWishlistResponse>> {
        return wishlistRepository.createWishlist(
            token, id
        )
    }

    fun deleteWishlist(
        token: String,
        id: Int
    ): LiveData<Resource<ChangeDataResponse>> {
        return wishlistRepository.deleteWishlist(
            token, id
        )
    }

}