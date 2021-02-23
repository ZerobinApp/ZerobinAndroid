package com.example.zerobin.ui.home.store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityWriteReviewSeedBinding

class WriteReviewSeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewSeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review_seed)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_review_seed)

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