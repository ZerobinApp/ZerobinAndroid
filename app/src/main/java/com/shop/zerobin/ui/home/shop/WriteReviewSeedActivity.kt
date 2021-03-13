package com.shop.zerobin.ui.home.shop

import android.content.Intent
import android.os.Bundle
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityWriteReviewSeedBinding
import com.shop.zerobin.ui.common.BaseBindingActivity

class WriteReviewSeedActivity :
    BaseBindingActivity<ActivityWriteReviewSeedBinding>(R.layout.activity_write_review_seed) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.seedImage.setOnClickListener {
            binding.seedImage.setBackgroundResource(R.drawable.ic_favorite_24px)
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}