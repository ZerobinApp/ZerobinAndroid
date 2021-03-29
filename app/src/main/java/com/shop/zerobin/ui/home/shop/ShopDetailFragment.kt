package com.shop.zerobin.ui.home.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentShopDetailBinding
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import com.shop.zerobin.util.GlideApp
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
            setImageFromFirebase(it.imageList)
            drawHashTagList(it.hashtagList)
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
                args?.shop?.zzim = zzim
                if (zzim) {
                    binding.btnZzim.setImageResource(R.drawable.ic_favorite_24px)
                } else {
                    binding.btnZzim.setImageResource(R.drawable.ic_favorite_border_24px)
                }
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
            writeReviewPage()
        }

        binding.writeReview.setOnClickListener {
            writeReviewPage()
        }

        binding.btnZzim.setOnClickListener {
            if (shopDetailViewModel.isLogin.value == true) {
                args?.shop?.let { shop ->
                    shopDetailViewModel.requestZzimShop(shop.shopIndex, shop.zzim.not())
                }
            } else {
                showLoginDialog()
            }
        }
    }

    private fun setImageFromFirebase(imageList: List<String>) {
        if (imageList.isEmpty()) {
            GlideApp.with(binding.shopImage.context)
                .load(ContextCompat.getDrawable(binding.shopImage.context, R.drawable.no_image))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.shopImage)
        } else {
            val spaceReference = Firebase.storage.reference.child(imageList[0])
            Log.e(TAG, spaceReference.toString())
            GlideApp.with(binding.shopImage.context)
                .load(spaceReference)
                .error(ContextCompat.getDrawable(binding.shopImage.context, R.drawable.no_image))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.shopImage)
        }
    }

    private fun writeReviewPage() {
        if (shopDetailViewModel.isLogin.value == true) {
            val action =
                ShopDetailFragmentDirections.actionNavigationShopDetailToNavigationWriteReviewSeed(
                    args?.shop
                )
            findNavController().navigate(action)
        } else {
            showLoginDialog()
        }
    }

    private fun drawHashTagList(hashtagList: List<ShopDetail.Hashtag>) {
        var hashTagString = ""
        hashtagList.forEach {
            hashTagString += "#${it.name} "
        }
        binding.hashTag.text = hashTagString
    }

    companion object {
        private val TAG: String = ShopDetailFragment::class.java.simpleName
    }
}