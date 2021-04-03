package com.shop.zerobin.ui.home.shop

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentWriteReviewSeedBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel


class WriteReviewSeedFragment :
    BaseBindingFragment<FragmentWriteReviewSeedBinding>(R.layout.fragment_write_review_seed) {

    private val args: WriteReviewSeedFragmentArgs? by navArgs()

    private val viewModel: WriteReviewViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeLiveData()
    }

    private fun setOnClickListener() {
        binding.seedInfo.setOnClickListener {
            showPopupWindow(it)
        }

        binding.seedImage.setOnClickListener {
            viewModel.bloomSeed()
        }

        binding.btnNext.setOnClickListener {
            val action =
                WriteReviewSeedFragmentDirections.actionNavigationWriteReviewSeedToNavigationWriteReview(
                    args?.shopIndex ?: 0, viewModel.seed.value!!
                )
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeLiveData() {
        viewModel.seed.observe(viewLifecycleOwner) {
            if (it) {
                binding.seedDescription1.visibility = View.GONE
                binding.seedDescription2.text = getString(R.string.review_seed_description_bloom)
                binding.seedImage.setImageResource(R.drawable.ic_pistachio_bloom)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showPopupWindow(view: View) {
        PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            contentView =
                LayoutInflater.from(requireContext()).inflate(R.layout.layout_seed_popup, null)
            isFocusable = true
            elevation = 12f
            animationStyle = android.R.style.Animation_Dialog

            showAtLocation(view, Gravity.CENTER, 0, 100)
        }
    }
}