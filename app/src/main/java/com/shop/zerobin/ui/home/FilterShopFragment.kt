package com.shop.zerobin.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentFilterShopBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.splash.filter.FilterViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FilterShopFragment :
        BaseBindingFragment<FragmentFilterShopBinding>(R.layout.fragment_filter_shop) {

    private val filterViewModel: FilterViewModel by viewModel()

    var hashtagList = ArrayList<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = filterViewModel

        requestFilterList()
        observeLiveData()
        setListeners()

    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            //필터 걸러지는 버튼
            for (i in 1..10) {

                if (requireView().findViewById<Chip>(R.id.chip + i).isChecked) {
                    hashtagList.add(i)
                }
            }
            filterViewModel.requestHashTagList(hashtagList)
            findNavController().navigate(R.id.action_navigation_shop_filter_to_navigation_home)

        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun requestFilterList() {
        filterViewModel.requestFilterList()
    }

    private fun observeLiveData() {
        filterViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
        filterViewModel.hashtagList.observe(viewLifecycleOwner) {

            for (i in it.indices) {
                requireView().findViewById<Chip>(R.id.chip + it[i].hashtagIndex).text =
                        "#" + it[i].name
            }
        }

        filterViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}