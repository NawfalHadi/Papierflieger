package com.papierflieger.presentation.ui.home.passenger

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.papierflieger.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.passenger_navContainer)
                as NavHostFragment

        navController = navHostFragment.navController
    }
}