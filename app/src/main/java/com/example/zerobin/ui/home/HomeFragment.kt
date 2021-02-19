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
import com.example.zerobin.ui.home.adapter.StoreAdapter
import com.example.zerobin.ui.home.store.StoreDetailActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private val storeAdapter by lazy { StoreAdapter() }

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

        setStoreAdapter()
        observeLiveData()
        requestStoreList()
        setListener()
    }

    private fun setStoreAdapter() {
        binding.storeRecyclerView.adapter = storeAdapter
    }

    private fun observeLiveData() {
        homeViewModel.storeList.observe(viewLifecycleOwner) {


            storeAdapter.setItem(it)
        }
    }

    private fun requestStoreList() {
        homeViewModel.requestStoreList()
    }

    private fun setListener() {
        storeAdapter.onClick = { store ->
            val intent = Intent(requireContext(), StoreDetailActivity::class.java).apply {
                putExtra(StoreDetailActivity.EXTRA_STORE, store)
            }
            startActivity(intent)
        }
    }
}