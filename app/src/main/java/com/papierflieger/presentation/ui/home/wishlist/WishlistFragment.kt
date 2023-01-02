package com.papierflieger.presentation.ui.home.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.databinding.FragmentWishlistBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.bussiness.WishlistViewModel
import com.papierflieger.presentation.ui.adapter.wishlist.WishlistAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private lateinit var binding : FragmentWishlistBinding
    private val wishlistViewModel : WishlistViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()

    private val adapterWishlist : WishlistAdapter by lazy {
        WishlistAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishlistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeData()
    }

    private fun observeData() {
        sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
            binding.apply {
                if (token != "") {
                    wishlistViewModel.getWishlist(token).observe(viewLifecycleOwner) { wishlist ->
                        when (wishlist) {
                            is Resource.Empty -> {}
                            is Resource.Error -> {}
                            is Resource.Loading -> {
                                pbLoading.visibility = View.VISIBLE
                                pbLoading.isVisible = true
                            }
                            is Resource.Success -> {
                                pbLoading.visibility = View.GONE
                                pbLoading.isVisible = false

                                if (!wishlist.payload?.wishlist.isNullOrEmpty()) {
                                    ivNotFound.visibility = View.GONE
                                    ivNotFound.isVisible = false

                                    tvNotFound.visibility = View.GONE
                                    tvNotFound.isVisible = false

                                    wishlist.payload?.let { adapterWishlist.setItem(it.wishlist) }
                                } else {
                                    rvWishlist.visibility = View.GONE
                                    rvWishlist.isVisible = false

                                    ivNotFound.visibility = View.VISIBLE
                                    ivNotFound.isVisible = true

                                    tvNotFound.visibility = View.VISIBLE
                                    tvNotFound.isVisible = true
                                }
                            }
                        }
                    }
                } else {
                    rvWishlist.visibility = View.GONE
                    rvWishlist.isVisible = false

                    pbLoading.visibility = View.GONE
                    pbLoading.isVisible = false

                    ivNotFound.visibility = View.VISIBLE
                    ivNotFound.isVisible = true

                    tvNotFound.visibility = View.VISIBLE
                    tvNotFound.isVisible = true
                }
            }

        }
    }

    private fun initList() {
        binding.rvWishlist.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterWishlist
        }

        adapterWishlist.actionClick(object : WishlistAdapter.OnWishlistItem{
            override fun itemWishlistClicked(id: Int) {
                findNavController().navigate(
                    R.id.action_wishlistFragment_to_detailDestinationActivity
                    ,Bundle().apply {
                        putInt("id", id)
                        putBoolean("wishlist", true)
                    }
                )
            }

            override fun wishlistClicked(id: Int) {
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    wishlistViewModel.deleteWishlist(token, id).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Empty -> {}
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                observeData()
                            }
                        }
                    }
                }
            }
        })
    }

}