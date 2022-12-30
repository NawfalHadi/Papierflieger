package com.papierflieger.presentation.ui.home.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.papierflieger.R
import com.papierflieger.databinding.FragmentHistoryBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.bussiness.NotificationViewModel
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val authViewModel : AuthViewModel by viewModels()
    private val notificationViewModel : NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingNotification()
        initList()
        observeData()
    }

    private fun observeData() {

    }

    private fun initList() {

    }

    private fun bindingNotification() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            notificationViewModel.getNotifications(token).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val notification = it.payload?.notifikasi?.count { notification -> !notification.read }
                        if (notification != null && notification > 0) {
                            updateNotification(token)
                        }
                    }
                }
            }
        }
    }

    private fun updateNotification(token: String) {
        notificationViewModel.updateNotification(token).observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(
                        R.id.bottomNavigation)
                    bottomNavigation.removeBadge(R.id.historyFragment)
                }
            }
        }
    }

}