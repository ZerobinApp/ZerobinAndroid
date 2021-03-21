package com.shop.zerobin.ui.review

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentReviewFilterBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewFilterFragment :
    BaseBindingFragment<FragmentReviewFilterBinding>(R.layout.fragment_review_filter) {

    private val reviewFilterViewModel: ReviewViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = reviewFilterViewModel


        setListeners()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}