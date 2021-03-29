package com.shop.zerobin.ui.review

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentReviewFilterBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.splash.filter.FilterViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewFilterFragment :
    BaseBindingFragment<FragmentReviewFilterBinding>(R.layout.fragment_review_filter) {

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

            val bundle = bundleOf("hashtag" to selectedHashTagList)
            findNavController().navigate(
                R.id.action_navigation_review_filter_to_navigation_review,
                bundle
            )

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
            for (i in 0 until binding.hashTagContainer.chipGroup.childCount) {
                view?.findViewWithTag<Chip>("${hashTagList[i].hashtagIndex}")?.text =
                    getString(R.string.hash_tag_format, hashTagList[i].name)
            }
        }

        filterViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private val TAG: String = ReviewFilterFragment::class.java.simpleName
    }
}