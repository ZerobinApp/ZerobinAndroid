package com.shop.zerobin.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentSearchShopBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.home.adapter.ShopAdapter
import com.shop.zerobin.ui.home.shop.ShopDetailFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class SearchShopFragment :
    BaseBindingFragment<FragmentSearchShopBinding>(R.layout.fragment_search_shop) {

    private val homeViewModel: HomeViewModel by viewModel()

    private val shopAdapter by lazy { ShopAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = homeViewModel


        observeLiveData()
        setShopAdapter()
        setListener()
        setSearchListener()
    }

    private fun setSearchListener() {
        binding.editTextSearchShop.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.editTextSearchShop.text.isNotEmpty()) {
                        homeViewModel.requestSearchShopList(s.toString())
                        binding.shopRecyclerView.isVisible = true
                        binding.imageViewSearchEmpty.isVisible = false
                        binding.textViewSearchEmpty.isVisible = false
                    } else {
                        binding.shopRecyclerView.isVisible = false
                        binding.imageViewSearchEmpty.isVisible = true
                        binding.textViewSearchEmpty.isVisible = true

                    }
                }
            })
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

    private fun setListener() {
        shopAdapter.onClick = { shop ->
            val action = ShopDetailFragmentDirections.actionGlobalNavigationShopDetail(shop)
            findNavController().navigate(action)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}