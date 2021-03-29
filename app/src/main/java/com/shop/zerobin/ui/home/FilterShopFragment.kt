package com.shop.zerobin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = filterViewModel

        requestFilterList()
        observeLiveData()
        setListeners()

    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            val selectedHashTagList = mutableListOf<Int>()
            binding.hashTagContainer.chipGroup.checkedChipIds.forEach { checkedId ->
                val chip = view?.findViewById<Chip>(checkedId)
                val index = chip?.tag.toString().toInt()
                Log.d(TAG, "해시태그 선택된 index : $index")
                selectedHashTagList.add(index)
            }
            filterViewModel.saveHashTagList(selectedHashTagList)
            findNavController().popBackStack()

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
        filterViewModel.hashtagList.observe(viewLifecycleOwner) { hashTagList ->
            val savedHashTagList = filterViewModel.getSavedHashTagList()
            for (i in 0 until binding.hashTagContainer.chipGroup.childCount) {
                view?.findViewWithTag<Chip>("${hashTagList[i].hashtagIndex}")?.text =
                    getString(R.string.hash_tag_format, hashTagList[i].name)
            }

            savedHashTagList.forEach {
                view?.findViewWithTag<Chip>("$it")?.isChecked = true
            }
        }

        filterViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private val TAG: String = FilterShopFragment::class.java.simpleName
    }
}