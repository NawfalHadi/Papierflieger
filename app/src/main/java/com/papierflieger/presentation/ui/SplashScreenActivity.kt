package com.papierflieger.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.papierflieger.R
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.admin.AdminActivity
import com.papierflieger.presentation.ui.auth.AuthActivity
import com.papierflieger.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val sessionViewModel : SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            sessionViewModel.getToken().observe(this){
                if (it.isEmpty()){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    sessionViewModel.getRole().observe(this) { role ->
                        if (role == "Customer") {
                            startActivity(Intent(this, HomeActivity::class.java))
                        } else {
                            startActivity(Intent(this, AdminActivity::class.java))
                        }
                        finish()
                    }
                }
            }
        }, 3000L)

    }

}