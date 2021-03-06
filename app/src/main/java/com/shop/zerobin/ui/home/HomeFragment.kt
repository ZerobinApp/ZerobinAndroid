package com.shop.zerobin.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentHomeBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.home.adapter.ShopAdapter
import com.shop.zerobin.ui.home.shop.ShopDetailFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()

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
        (binding.shopRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
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
            val action = ShopDetailFragmentDirections.actionGlobalNavigationShopDetail(shop.shopIndex)
            findNavController().navigate(action)
        }

        shopAdapter.onZzimClick = { shop, position ->
            if (homeViewModel.isLogin.value == true) {
                homeViewModel.requestZzimShop(shop.shopIndex)
                shop.zzim = shop.zzim.not()
                shopAdapter.notifyItemChanged(position)
            } else {
                showLoginDialog()
            }
        }

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_search)
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_filter)
        }
    }

    companion object {
        private val TAG: String = HomeFragment::class.java.simpleName
    }
}