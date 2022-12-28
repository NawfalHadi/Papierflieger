package com.papierflieger.presentation.ui.home.destination

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.papierflieger.R
import com.papierflieger.databinding.ActivityDetailDestinationBinding
import com.papierflieger.presentation.bussiness.DestinationViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.bussiness.WishlistViewModel
import com.papierflieger.presentation.ui.adapter.destinations.DestinationPictureAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDestinationActivity : AppCompatActivity() {

    private val destinationId: Int by lazy {
        intent?.getIntExtra("id", -1) as Int
    }

    private val wishlistItem: Boolean by lazy {
        intent?.getBooleanExtra("wishlist", false) as Boolean
    }

    private val binding: ActivityDetailDestinationBinding by lazy {
        ActivityDetailDestinationBinding.inflate(layoutInflater)
    }

    private val destinationViewModel : DestinationViewModel by viewModels()
    private val wishlistViewModel : WishlistViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private val destinationPictureAdapter : DestinationPictureAdapter by lazy {
        DestinationPictureAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            cbFavorite.setOnClickListener {
                if (cbFavorite.isChecked) {
                    favoriteItem()
                } else {
                    deleteItem()
                }
            }
            btnBook.setOnClickListener {

            }
        }
    }

    private fun favoriteItem() {
        sessionViewModel.getToken().observe(this) { token ->
            wishlistViewModel.createWishlist(token, destinationId).observe(this) {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Toast.makeText(applicationContext, it.payload.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun deleteItem() {
        sessionViewModel.getToken().observe(this) { token ->
            wishlistViewModel.deleteWishlist(token, destinationId).observe(this) {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Toast.makeText(applicationContext, it.payload.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeData() {
        if (destinationId > -1) {
            destinationViewModel.getDestinationById(destinationId).observe(this) {
                binding.apply {
                    if (it.destination.image?.get(0).isNullOrEmpty().not()) {
                        ivBackground.load(it.destination.image?.get(0)) {
                            placeholder(R.color.background_gray)
                        }
                    }

                    tvDestinationName.text = it.destination.name
                    tvLocation.text = it.destination.location
                    tvDescriptionValue.text = it.destination.description
                    cbFavorite.isChecked = wishlistItem

                    it.destination.image?.let { picture -> destinationPictureAdapter.setItem(picture) }
                }
            }
        }
    }

    private fun initList() {
        binding.rvDestinationPicture.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = destinationPictureAdapter
        }
    }

}