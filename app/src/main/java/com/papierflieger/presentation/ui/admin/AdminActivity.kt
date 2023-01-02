package com.papierflieger.presentation.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.papierflieger.R
import com.papierflieger.databinding.ActivityAdminBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminBinding
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var navController: NavController

    private val sessionViewModel : SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingSession()

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

    @SuppressLint("InflateParams", "ResourceAsColor")
    private fun bindingSession() {
        val header = layoutInflater.inflate(R.layout.item_header_navigation_drawer, null)

        val tvName: TextView = header.findViewById(R.id.tv_name)
        val tvEmail: TextView = header.findViewById(R.id.tv_email)
        val ivAvatar: ImageView = header.findViewById(R.id.iv_avatar)

        sessionViewModel.getName().observe(this@AdminActivity) {
            tvName.text = it
        }

        sessionViewModel.getEmail().observe(this@AdminActivity){
            tvEmail.text = it
        }

        sessionViewModel.getAvatar().observe(this@AdminActivity){
            ivAvatar.load(it) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_person_circle)
            }
        }

        binding.navMenuAdmin.addHeaderView(header)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}