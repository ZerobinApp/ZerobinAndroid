package com.example.zerobin.ui.home.shop

import android.os.Bundle
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityWriteReviewBinding
import com.example.zerobin.ui.common.BaseBindingActivity

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