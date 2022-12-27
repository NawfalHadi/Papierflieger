package com.papierflieger.presentation.ui.home.passenger

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.papierflieger.R
import com.papierflieger.data.network.response.ticket.DataTicket
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val args : PassengerActivityArgs by navArgs()

    private lateinit var ticketsPreview : ArrayList<DataTicket>


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.passenger_navContainer)
                as NavHostFragment

        navController = navHostFragment.navController
        initData()
    }

    private fun initData() {
        ticketsPreview = args.previews.toCollection(ArrayList())
    }

    companion object {
        const val PASSENGER_KEY = "PASSENGER_KEY"
    }

}