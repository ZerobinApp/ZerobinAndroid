package com.example.zerobin.ui.home.shop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityShopDetailBinding
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.ui.common.BaseBindingActivity
import com.example.zerobin.ui.review.adapter.ReviewAdapter

class ShopDetailActivity : BaseBindingActivity<ActivityShopDetailBinding>(R.layout.activity_shop_detail) {

    private val shopDetailViewModel: ShopDetailViewModel by viewModels()

    private val reviewShopAdapter by lazy { ReviewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = shopDetailViewModel

        requestShopDetailData()
        setReviewAdapter()
        observeLiveData()
        setOnClickListener()
    }

    private fun requestShopDetailData() {
        val shop = intent?.getParcelableExtra(EXTRA_SHOP) as? Shop
        shop?.let {
            shopDetailViewModel.requestShopDetailData(it)
        } ?: run {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun observeLiveData() {
        shopDetailViewModel.shopDetail.observe(this) {
            reviewShopAdapter.setDetailReviewItem(it)
        }

        shopDetailViewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        shopDetailViewModel.isError.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setReviewAdapter() {
        binding.detailReviewRecyclerView.adapter = reviewShopAdapter
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, WriteReviewSeedActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_SHOP = "EXTRA_SHOP"
    }
}