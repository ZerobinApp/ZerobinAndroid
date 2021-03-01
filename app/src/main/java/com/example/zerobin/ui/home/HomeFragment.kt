package com.example.zerobin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentHomeBinding
import com.example.zerobin.ui.home.adapter.ShopAdapter
import com.example.zerobin.ui.home.shop.ShopDetailActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private val shopAdapter by lazy { ShopAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = homeViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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