package com.shop.zerobin.ui.home.shop

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentWriteReviewSeedBinding
import com.shop.zerobin.ui.common.BaseBindingFragment

class WriteReviewSeedFragment :
    BaseBindingFragment<FragmentWriteReviewSeedBinding>(R.layout.fragment_write_review_seed) {

    private val args: WriteReviewSeedFragmentArgs? by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.seedImage.setOnClickListener {
            binding.seedImage.setBackgroundResource(R.drawable.ic_favorite_24px)
        }

        binding.btnNext.setOnClickListener {
            val action =
                WriteReviewSeedFragmentDirections.actionNavigationWriteReviewSeedToNavigationWriteReview(
                    args?.shop)
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}