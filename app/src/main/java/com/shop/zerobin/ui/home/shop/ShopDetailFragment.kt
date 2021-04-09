package com.shop.zerobin.ui.home.shop

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentShopDetailBinding
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.common.ImageViewPagerActivity
import com.shop.zerobin.ui.home.adapter.FeatureAdapter
import com.shop.zerobin.ui.home.adapter.ShopImageAdapter
import com.shop.zerobin.ui.review.DialogArticleTap
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ShopDetailFragment :
    BaseBindingFragment<FragmentShopDetailBinding>(R.layout.fragment_shop_detail) {

    private val shopDetailViewModel: ShopDetailViewModel by viewModel()

    private val reviewShopAdapter by lazy {
        ReviewAdapter().apply {
            viewType = ReviewAdapter.ViewType.SHOP
            onImageClick = { reviewIndex, position ->
                val intent = Intent(requireContext(), ImageViewPagerActivity::class.java)
                val imageList: Array<String> =
                    shopDetailViewModel.shopDetail.value?.reviewList?.get(reviewIndex)?.imageList?.toTypedArray()
                        ?: emptyArray()
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_LIST, imageList)
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_INDEX, position)
                startActivity(intent)
            }
        }
    }

    private val featureAdapter by lazy { FeatureAdapter() }

    private val shopImageAdapter by lazy {
        ShopImageAdapter().apply {
            onClick = { position ->
                val intent = Intent(requireContext(), ImageViewPagerActivity::class.java)
                val imageList: Array<String> =
                    shopDetailViewModel.shopDetail.value?.imageList?.toTypedArray() ?: emptyArray()
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_LIST, imageList)
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_INDEX, position)
                startActivity(intent)
            }
        }
    }

    private val args: ShopDetailFragmentArgs? by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = shopDetailViewModel

        requestShopDetailData()
        setReviewAdapter()
        setFeatureAdapter()
        setShopImageAdapter()
        observeLiveData()
        setOnClickListener()
    }

    private fun requestShopDetailData() {
        Log.d(TAG, "Shop >> $args")
        args?.shopIndex?.let {
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
            reviewShopAdapter.setItem(it.reviewList)
            featureAdapter.setItem(it.hashtagList)
            shopImageAdapter.setItem(it.imageList)
            drawHashTagList(it.hashtagList)

            if (it.reviewList.isEmpty()) {
                binding.reviewEmptyImageView.isVisible = true
                binding.reviewEmptyTextView.isVisible = true
            }
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

        shopDetailViewModel.zzimSuccess.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { zzim ->
                shopDetailViewModel.shopDetail.value?.zzim = zzim
                if (zzim) {
                    binding.btnZzim.setImageResource(R.drawable.heart_zzim)
                } else {
                    binding.btnZzim.setImageResource(R.drawable.heart_zzim_not)
                }
            }
        }
    }

    private fun setReviewAdapter() {
        binding.detailReviewRecyclerView.adapter = reviewShopAdapter
    }

    private fun setFeatureAdapter() {
        binding.featureRecyclerView.adapter = featureAdapter
    }

    private fun setShopImageAdapter() {
        binding.shopImageViewPager.adapter = shopImageAdapter
        binding.dotsIndicator.setViewPager2(binding.shopImageViewPager)
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.writeReview.setOnClickListener {
            writeReviewPage()
        }

        binding.btnZzim.setOnClickListener {
            if (shopDetailViewModel.isLogin.value == true) {
                shopDetailViewModel.shopDetail.value?.let { shop ->
                    shopDetailViewModel.requestZzimShop(shop.shopIndex, shop.zzim.not())
                }
            } else {
                showLoginDialog()
            }
        }

        binding.addressCopy.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val textToCopy = shopDetailViewModel.shopDetail.value?.location
            val clip = ClipData.newPlainText("text", textToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "주소가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }

        reviewShopAdapter.setOnItemClickListener(object : ReviewAdapter.OnItemClickListener {
            override fun onItemClick(review: Review) {}

            override fun onMenuClick(review: Review) {
                if (review.owner) {
                    DialogArticleTap().apply {
                        viewType = DialogArticleTap.ViewType.MINE
                        onClick = { clickType ->
                            when (clickType) {
                                DialogArticleTap.ClickType.EDIT -> {
                                    args?.shopIndex?.let {
                                        val list = mutableListOf(it, review.reviewIndex)
                                        val bundle = bundleOf("list" to list)

                                        findNavController()
                                            .navigate(R.id.action_global_navigation_write_review,
                                                bundle)
                                    }
                                }
                                DialogArticleTap.ClickType.DELETE -> {
                                    shopDetailViewModel.requestReviewDelete(review.reviewIndex)
                                }
                                else -> Toast.makeText(
                                    requireContext(),
                                    "잘못된 접근",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }.show(childFragmentManager, "dialog.tag")
                } else {
                    DialogArticleTap().apply {
                        viewType = DialogArticleTap.ViewType.OTHER
                        onClick = { clickType ->
                            when (clickType) {
                                DialogArticleTap.ClickType.REPORT -> {
                                    shopDetailViewModel.requestReviewReport(review.reviewIndex)
                                }
                                else -> Toast.makeText(
                                    requireContext(),
                                    "잘못된 접근",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }.show(childFragmentManager, "dialog.tag")
                }
            }
        })
    }

    private fun writeReviewPage() {
        if (shopDetailViewModel.isLogin.value == true) {

            shopDetailViewModel.shopDetail.value?.reviewList?.forEach {
                if (it.owner) {
                    Toast.makeText(requireContext(), "이미 작성한 리뷰가 있습니다.", Toast.LENGTH_LONG).show()
                    return
                }
            }

            val action =
                ShopDetailFragmentDirections.actionNavigationShopDetailToNavigationWriteReviewSeed(
                    args?.shopIndex ?: 0
                )
            findNavController().navigate(action)
        } else {
            showLoginDialog()
        }
    }

    private fun drawHashTagList(hashTagList: List<ShopDetail.Hashtag>) {
        var hashTagString = ""
        hashTagList.forEach {
            hashTagString += getString(R.string.hash_tag_format, it.name) + "  "
        }
        binding.hashTag.text = hashTagString
    }

    companion object {
        private val TAG: String = ShopDetailFragment::class.java.simpleName
    }
}