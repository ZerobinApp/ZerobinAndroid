package com.shop.zerobin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.shop.zerobin.R
import com.shop.zerobin.ui.splash.filter.FilterActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startFilterActivity()
    }

    private fun startFilterActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FilterActivity::class.java))
            finish()
        }, 2000)
    }
}