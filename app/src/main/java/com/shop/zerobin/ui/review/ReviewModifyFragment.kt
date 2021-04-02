package com.shop.zerobin.ui.review

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentReviewModifyBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.home.shop.WriteReviewFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewModifyFragment: BaseBindingFragment<FragmentReviewModifyBinding>(R.layout.fragment_review_modify) {

    private val reviewViewModel: ReviewViewModel by viewModel()

    var shop=0
    var review=0

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
         shop= arguments?.getIntegerArrayList("list")?.get(0)!!
         review=arguments?.getIntegerArrayList("list")?.get(1)!!
        reviewViewModel.requestGetReview(shop,review)

    }

    fun observeLiveData(){
        reviewViewModel.hashTagList.observe(viewLifecycleOwner) { hashTagList ->
            for (i in 0 until binding.hashTagContainer.chipGroup.childCount) {
                view?.findViewWithTag<Chip>("${hashTagList[i].hashtagIndex}")?.text =
                    getString(R.string.hash_tag_format, hashTagList[i].name)


            }
        }
        reviewViewModel.reviewDetail.observe(viewLifecycleOwner){
            it.hashtagList.forEach{
                hashtag ->view?.findViewWithTag<Chip>("${hashtag.hashtagIndex}")?.isChecked=true

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

        binding.btnComplete.setOnClickListener {
            val updateImageList = emptyList<String>()
            val deleteImageList = emptyList<String>()
            val selectedHashTagList = mutableListOf<Int>()
            val deleteHashTagList= mutableListOf<Int>()
            binding.hashTagContainer.chipGroup.checkedChipIds.forEach { checkedId ->
                val chip = view?.findViewById<Chip>(checkedId)
                val index = chip?.tag.toString().toInt()
                selectedHashTagList.add(index)

            }
            for(i in 1..10){
                if(!selectedHashTagList.contains(i)){
                    deleteHashTagList.add(i)
                }
            }

            reviewViewModel.postModifyReview(selectedHashTagList,deleteHashTagList,updateImageList,deleteImageList,touchSeed,shop,review,binding.inputReview.text.toString())
            findNavController().popBackStack()
        }

        //수정하기 버튼
    }
}