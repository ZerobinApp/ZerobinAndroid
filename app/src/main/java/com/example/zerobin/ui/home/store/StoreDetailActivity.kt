package com.example.zerobin.ui.home.store

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.ActivityStoreDetailBinding
import com.example.zerobin.model.Store

class StoreDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreDetailBinding
    private lateinit var storeDetailViewModel: StoreDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeDetailViewModel = ViewModelProvider(this).get(StoreDetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        binding.lifecycleOwner = this
        binding.vm = storeDetailViewModel

        requestStoreDetailData()
    }

    private fun requestStoreDetailData() {
        val store = intent?.getParcelableExtra(EXTRA_STORE) as? Store
        store?.let {
            storeDetailViewModel.requestStoreDetailData(it)
        } ?: run {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    companion object {
        const val EXTRA_STORE = "EXTRA_STORE"
    }
}