package com.example.zerobin.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageShopBinding
import com.example.zerobin.ui.home.adapter.ShopAdapter
import com.example.zerobin.ui.home.shop.ShopDetailActivity


class MyPageShopFragment : Fragment() {

    private lateinit var binding: FragmentMyPageShopBinding

    private lateinit var myPageViewModel: MyPageViewModel

    private val shopAdapter by lazy { ShopAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        myPageViewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_shop, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = myPageViewModel

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
        myPageViewModel.myUser.observe(viewLifecycleOwner) {
//            shopAdapter.setItem(it.favoriteShop)

        }
    }

    private fun requestShopList() {
        myPageViewModel.requestMyPage()
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