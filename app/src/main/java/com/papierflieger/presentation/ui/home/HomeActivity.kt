package com.papierflieger.presentation.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.papierflieger.R
import com.papierflieger.databinding.ActivityHomeBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.bussiness.NotificationViewModel
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding : ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val authViewModel: AuthViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindingNavigation()
        bindingNotification()
    }

    override fun onResume() {
        super.onResume()
        bindingNotification()
    }

    private fun bindingNotification() {
        authViewModel.getToken().observe(this) { token ->
            notificationViewModel.getNotifications(token).observe(this) {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val notification = it.payload?.notifikasi?.count { notification -> !notification.read }
                        if (notification != null && notification > 0) {
                            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.historyFragment)
                            badge.number = notification
                            badge.isVisible = true
                        } else {
                            binding.bottomNavigation.removeBadge(R.id.historyFragment)
                        }
                    }
                }
            }
        }
    }

    private fun bindingNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_navContainer)
                as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

}