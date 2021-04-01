package com.shop.zerobin.ui.review

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentReviewModifyBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewModifyFragment: BaseBindingFragment<FragmentReviewModifyBinding>(R.layout.fragment_review_modify) {

    private val reviewViewModel: ReviewViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm= reviewViewModel
        reviewViewModel.setHashTagList()
       requestGetReview()
        observeLiveData()
        setListeners()
    }

    private fun requestGetReview(){
        val shop=arguments?.getIntegerArrayList("list")?.get(0)
        val review=arguments?.getIntegerArrayList("list")?.get(1)
        if (review != null) {
            if (shop != null) {
                reviewViewModel.requestGetReview(shop,review)
            }
        }

    }





    fun observeLiveData(){
        reviewViewModel.hashTagList.observe(viewLifecycleOwner) { hashTagList ->
            for (i in 0 until binding.hashTagContainer.chipGroup.childCount) {
                view?.findViewWithTag<Chip>("${hashTagList[i].hashtagIndex}")?.text =
                    getString(R.string.hash_tag_format, hashTagList[i].name)


            }
        }
        reviewViewModel.reviewDetail.observe(viewLifecycleOwner){

            for(i in it.hashtagList.indices) {

                for(j in 1..10){
                    val chip = view?.findViewById<Chip>(R.id.chip+j)
                    if(chip?.tag==(it.hashtagList[i].hashtagIndex)) {
                        chip.isChecked = true
                    }
                }
            }

        }
        reviewViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        reviewViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setListeners(){
        var touchSeed=true
        binding.imageSeed.setOnClickListener {
            touchSeed = if(touchSeed) {
                binding.imageSeed.setImageResource(R.drawable.ic_pistachio)
                binding.imageSeed.setBackgroundResource(R.drawable.seed_bg)
                false
            }else{
                binding.imageSeed.setImageResource(R.drawable.ic_pistachio_bloom)
                binding.imageSeed.setBackgroundResource(R.drawable.seed_bg_bloom)

                true
            }


        }
        //수정하기 버튼
    }
}