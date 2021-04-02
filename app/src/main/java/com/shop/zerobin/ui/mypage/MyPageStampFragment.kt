package com.shop.zerobin.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageStampBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.common.ImageViewPagerActivity
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class MyPageStampFragment :
    BaseBindingFragment<FragmentMyPageStampBinding>(R.layout.fragment_my_page_stamp) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    private val myPageStampAdapter by lazy {
        ReviewAdapter().apply {
            onImageClick = { reviewIndex, position ->
                val intent = Intent(requireContext(), ImageViewPagerActivity::class.java)
                val imageList: Array<String> =
                    myPageViewModel.myUserStamp.value?.get(reviewIndex)?.imageList?.toTypedArray()
                        ?: emptyArray()
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_LIST, imageList)
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_INDEX, position)
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = myPageViewModel

        setReviewAdapter()
        observeLiveData()
        requestShopList()
    }

    private fun setReviewAdapter() {
        binding.myStampRecyclerView.adapter = myPageStampAdapter
    }

    private fun observeLiveData() {
        myPageViewModel.myUserStamp.observe(viewLifecycleOwner) {
            myPageStampAdapter.setItem(it)
            if (it.isEmpty()) {
                binding.stampEmptyImageView.isVisible = true
                binding.stampEmptyTextView.isVisible = true
            }
        }

        myPageViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        myPageViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestShopList() {
        myPageViewModel.requestMyPageStamp()
    }
}