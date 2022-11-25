package com.papierflieger.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.papierflieger.R
import com.papierflieger.databinding.ActivitySplashScreenBinding
import com.papierflieger.presentation.ui.auth.AuthActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val LOADING_TIME = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, LOADING_TIME)

    }
}