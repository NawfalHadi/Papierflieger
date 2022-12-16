package com.papierflieger.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.papierflieger.R
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.ui.auth.AuthActivity
import com.papierflieger.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            authViewModel.getToken().observe(this){
                if (it.isEmpty()){
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        }, LOADING_TIME)

    }

    companion object {
        const val LOADING_TIME = 3000L
    }
}