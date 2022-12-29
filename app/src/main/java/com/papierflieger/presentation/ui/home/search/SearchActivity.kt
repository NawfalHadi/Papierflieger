package com.papierflieger.presentation.ui.home.search

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.papierflieger.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.search_navContainer)
                as NavHostFragment

        navController = navHostFragment.navController
    }

    companion object {
        const val RETURN_TICKETS_KEY = "RETURN_TICKET_KEY"
        const val DEPARTURE_TICKET_KEY = "DEPARTURE_TICKET_KEY"
        const val TICKETS_KEY = "TICKETS_KEY"

        const val AIRPORT_FROM_KEY = "AIRPORT_FROM_KEY"
        const val AIRPORT_TO_KEY = "AIRPORT_TO_KEY"
        const val DATE_DEPARTURE_KEY = "DATE_DEPARTURE_KEY"
        const val DATE_RETURN_KEY = "DATE_RETURN_KEY"
        const val PASSENGER_COUNTER_KEY = "PASSENGER_COUNTER_KEY"

        const val PASSENGER_KEY = "PASSENGER_KEY"
    }
}