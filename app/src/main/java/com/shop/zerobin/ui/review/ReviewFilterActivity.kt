package com.shop.zerobin.ui.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityFilterBinding
import com.shop.zerobin.databinding.ActivityReviewFilterBinding
import com.shop.zerobin.ui.MainActivity
import com.shop.zerobin.ui.common.BaseBindingActivity
import com.shop.zerobin.ui.splash.filter.FilterViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewFilterActivity : BaseBindingActivity<ActivityReviewFilterBinding>(R.layout.activity_review_filter) {


    private val reviewFilterViewModel: ReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm=reviewFilterViewModel

        setListeners()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            finish()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}