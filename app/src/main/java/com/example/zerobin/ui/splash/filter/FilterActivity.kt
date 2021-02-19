package com.example.zerobin.ui.splash.filter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.example.zerobin.MainActivity
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        binding.lifecycleOwner = this
        binding.filterSelectNextBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}