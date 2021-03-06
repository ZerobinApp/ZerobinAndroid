package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageShopBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.home.adapter.ShopAdapter
import com.shop.zerobin.ui.home.shop.ShopDetailFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel


class MyPageShopFragment :
    BaseBindingFragment<FragmentMyPageShopBinding>(R.layout.fragment_my_page_shop) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    private val shopAdapter by lazy { ShopAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = myPageViewModel

        setShopAdapter()
        observeLiveData()
        requestShopList()
        setListener()
    }

    private fun setShopAdapter() {
        binding.shopRecyclerView.adapter = shopAdapter
    }

    private fun observeLiveData() {
        myPageViewModel.myUserShop.observe(viewLifecycleOwner) {
            shopAdapter.setItem(it)
            if (it.isEmpty()) {
                binding.shopEmptyImageView.isVisible = true
                binding.shopEmptyTextView.isVisible = true
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
        myPageViewModel.requestMyPageShop()
    }

    private fun setListener() {
        shopAdapter.onClick = { shop ->
            val action = ShopDetailFragmentDirections.actionGlobalNavigationShopDetail(shop.shopIndex)
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}