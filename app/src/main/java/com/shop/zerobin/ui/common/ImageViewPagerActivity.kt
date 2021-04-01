package com.shop.zerobin.ui.common

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityImageViewPagerBinding

class ImageViewPagerActivity :
    BaseBindingActivity<ActivityImageViewPagerBinding>(R.layout.activity_image_view_pager) {

    private val imageAdapter by lazy { ImageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor()
        setViewPager()
        setOnClickListeners()
    }

    private fun setStatusBarColor() {
        window?.statusBarColor = getColor(R.color.black)
        window?.decorView?.systemUiVisibility = 0
    }

    private fun setViewPager() {
        binding.imageViewPager.adapter = imageAdapter

        val imageList = intent?.getStringArrayExtra(EXTRA_IMAGE_LIST) ?: emptyArray()
        val position = intent?.getIntExtra(EXTRA_IMAGE_INDEX, 0) ?: 0

        imageAdapter.setItem(imageList.toList())

        val onPageSelectedCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val pageText = binding.root.context.getString(R.string.image_page_format,
                    position + 1,
                    imageList.size)
                binding.page.text = pageText
            }
        }
        binding.imageViewPager.registerOnPageChangeCallback(onPageSelectedCallback)
        onPageSelectedCallback.onPageSelected(position)
        binding.imageViewPager.setCurrentItem(position, false)
    }

    private fun setOnClickListeners() {
        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_IMAGE_LIST = "EXTRA_IMAGE_LIST"
        const val EXTRA_IMAGE_INDEX = "EXTRA_IMAGE_INDEX"
    }
}