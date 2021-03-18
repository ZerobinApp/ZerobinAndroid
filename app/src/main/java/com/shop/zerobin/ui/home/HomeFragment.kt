package com.shop.zerobin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentHomeBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.home.adapter.ShopAdapter
import com.shop.zerobin.ui.home.shop.ShopDetailActivity

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private val shopAdapter by lazy { ShopAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = homeViewModel

        setShopAdapter()
        observeLiveData()
        requestShopList()
        setListener()
    }

    private fun setShopAdapter() {
        binding.shopRecyclerView.adapter = shopAdapter
    }

    private fun observeLiveData() {
        homeViewModel.shopList.observe(viewLifecycleOwner) {
            shopAdapter.setItem(it)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        homeViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestShopList() {
        homeViewModel.requestShopList()
    }

    private fun setListener() {
        shopAdapter.onClick = { shop ->
            val intent = Intent(requireContext(), ShopDetailActivity::class.java).apply {
                putExtra(ShopDetailActivity.EXTRA_SHOP, shop)
            }
            startActivity(intent)
        }
    }
}