package com.example.zerobin.ui.notifications

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageStoreBinding
import com.example.zerobin.ui.home.adapter.StoreAdapter
import com.example.zerobin.ui.home.store.StoreDetailActivity


class MyPageStoreFragment : Fragment() {

    private lateinit var binding: FragmentMyPageStoreBinding

    private lateinit var myPageViewModel: MyPageViewModel

    private val storeAdapter by lazy { StoreAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        myPageViewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_store, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = myPageViewModel

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
        myPageViewModel.myUser.observe(viewLifecycleOwner) {
            storeAdapter.setItem(it.favoriteStore)

        }
    }

    private fun requestStoreList() {
        myPageViewModel.requestMyPage()
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