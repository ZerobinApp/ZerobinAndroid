package com.example.zerobin.ui.home.shop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityShopDetailBinding
import com.example.zerobin.model.Shop
import com.example.zerobin.ui.dashboard.adapter.ReviewAdapter

class ShopDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopDetailBinding
    private lateinit var shopDetailViewModel: ShopDetailViewModel

    private val reviewShopAdapter by lazy { ReviewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shopDetailViewModel = ViewModelProvider(this).get(ShopDetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_detail)
        binding.lifecycleOwner = this
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