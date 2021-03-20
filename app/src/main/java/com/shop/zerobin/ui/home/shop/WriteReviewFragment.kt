package com.shop.zerobin.ui.home.shop

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentWriteReviewBinding
import com.shop.zerobin.ui.common.BaseBindingFragment

class WriteReviewFragment :
    BaseBindingFragment<FragmentWriteReviewBinding>(R.layout.fragment_write_review) {

    private val args: WriteReviewFragmentArgs? by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnComplete.setOnClickListener {
            val action =
                WriteReviewFragmentDirections.actionNavigationWriteReviewToNavigationShopDetail(args?.shop)
            findNavController().navigate(action)
        }
    }
}