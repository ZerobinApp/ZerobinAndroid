package com.shop.zerobin.ui.splash.filter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityFilterBinding
import com.shop.zerobin.ui.MainActivity
import com.shop.zerobin.ui.common.BaseBindingActivity

class FilterActivity : BaseBindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {

    private val filterViewModel: FilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = filterViewModel

        requestFilterList()
        observeLiveData()
        startMainActivity()
    }

    private fun requestFilterList() {
        filterViewModel.requestFilterList()
    }

    private fun observeLiveData() {
        filterViewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        filterViewModel.isError.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startMainActivity() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}