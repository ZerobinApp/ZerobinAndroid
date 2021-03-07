package com.example.zerobin.ui.splash.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.ui.MainActivity
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityFilterBinding
import com.example.zerobin.ui.home.HomeViewModel

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding
    private val filterViewModel: FilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        binding.vm = filterViewModel
        binding.lifecycleOwner = this

        requestFilterList()
        startMainActivity()
    }

    private fun requestFilterList() {
        filterViewModel.requestFilterList()
    }

    private fun startMainActivity() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}