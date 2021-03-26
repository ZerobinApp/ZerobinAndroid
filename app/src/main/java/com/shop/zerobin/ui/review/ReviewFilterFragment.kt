package com.shop.zerobin.ui.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
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
    var hashtagList = arrayListOf<Int>()
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

            val bundle = bundleOf("hashtag" to hashtagList)
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