package com.shop.zerobin.ui.home.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentShopDetailBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ShopDetailFragment :
    BaseBindingFragment<FragmentShopDetailBinding>(R.layout.fragment_shop_detail) {

    private val shopDetailViewModel: ShopDetailViewModel by viewModel()

    private val reviewShopAdapter by lazy { ReviewAdapter() }

    private val args: ShopDetailFragmentArgs? by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = shopDetailViewModel

        requestShopDetailData()
        setReviewAdapter()
        observeLiveData()
        setOnClickListener()
    }

    private fun requestShopDetailData() {
        Log.d(TAG, "Shop >> $args")
        args?.shop?.let {
            shopDetailViewModel.requestShopDetailData(it)
        } ?: run {
            Toast.makeText(requireContext(), "잘못된 접근입니다.", Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                findNavController().popBackStack()
            }
        }
    }

    private fun observeLiveData() {
        shopDetailViewModel.shopDetail.observe(viewLifecycleOwner) {
            reviewShopAdapter.setDetailReviewItem(it)
        }

        shopDetailViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        shopDetailViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setReviewAdapter() {
        binding.detailReviewRecyclerView.adapter = reviewShopAdapter
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.floatingActionButton.setOnClickListener {
            val action =
                ShopDetailFragmentDirections.actionNavigationShopDetailToNavigationWriteReviewSeed(
                    args?.shop)
            findNavController().navigate(action)
        }
    }

    companion object {
        private val TAG: String = ShopDetailFragment::class.java.simpleName
    }
}