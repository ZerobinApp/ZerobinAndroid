package com.shop.zerobin.ui.home.shop

import android.os.Bundle
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityWriteReviewBinding
import com.shop.zerobin.ui.common.BaseBindingActivity

class WriteReviewActivity : BaseBindingActivity<ActivityWriteReviewBinding>(R.layout.activity_write_review) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnComplete.setOnClickListener {
            finish()
        }
    }
}