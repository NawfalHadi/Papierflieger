package com.papierflieger.presentation.ui.admin

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.papierflieger.R
import com.papierflieger.databinding.ActivityAdminBinding
import com.papierflieger.presentation.ui.admin.airplane.AdminAirplaneFragment
import com.papierflieger.presentation.ui.admin.airport.AdminAirportFragment
import com.papierflieger.presentation.ui.admin.dashboard.AdminDashboardFragment
import com.papierflieger.presentation.ui.admin.destination.AdminDestinationFragment
import com.papierflieger.presentation.ui.admin.payment.AdminPaymentFragment
import com.papierflieger.presentation.ui.admin.ticket.AdminTicketFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminBinding
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toggle = ActionBarDrawerToggle(this@AdminActivity, dlAdminActivity, R.string.open, R.string.close)
            dlAdminActivity.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.admin_dashboard_nav_container)
                    as NavHostFragment

            navController = navHostFragment.navController
            navMenuAdmin.setupWithNavController(navController)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}