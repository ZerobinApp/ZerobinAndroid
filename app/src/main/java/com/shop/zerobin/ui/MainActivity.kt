package com.shop.zerobin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shop.zerobin.R

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNav()
    }

    private fun setupNav() {
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.itemIconTintList = null

        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_review, R.id.navigation_home, R.id.navigation_my_page -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottomNavigationView.translationY = 0f
    }

    private fun hideBottomNav() {
        bottomNavigationView.animate()
            .setDuration(500)
            .translationY(bottomNavigationView.y)
    }
}