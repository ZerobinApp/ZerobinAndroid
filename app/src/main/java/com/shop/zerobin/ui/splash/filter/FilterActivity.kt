package com.shop.zerobin.ui.splash.filter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityFilterBinding
import com.shop.zerobin.ui.MainActivity
import com.shop.zerobin.ui.common.BaseBindingActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FilterActivity : BaseBindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {

    private val filterViewModel: FilterViewModel by viewModel()

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
        filterViewModel.hashtagList.observe(this) {

            for (i in it.indices) {
                findViewById<Chip>(R.id.chip + it[i].hashtagIndex).text = it[i].name
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