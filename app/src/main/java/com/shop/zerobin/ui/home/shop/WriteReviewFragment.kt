package com.shop.zerobin.ui.home.shop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentWriteReviewBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel


class WriteReviewFragment :
        BaseBindingFragment<FragmentWriteReviewBinding>(R.layout.fragment_write_review) {

    private val args: WriteReviewFragmentArgs? by navArgs()

    private val viewModel: WriteReviewViewModel by viewModel()

    private val requestGallery1 = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(TAG, activityResult.toString())
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.data?.let {
                setImage(it, 1)
            }
        }
    }

    private val requestGallery2 = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(TAG, activityResult.toString())
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.data?.let {
                setImage(it, 2)
            }
        }
    }

    private val requestGallery3 = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(TAG, activityResult.toString())
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.data?.let {
                setImage(it, 3)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setViewModelData()
        setOnClickListener()
        observeLiveData()
    }

    private fun setViewModelData() {
        if (args?.seed == true) viewModel.bloomSeed()
        args?.shop?.let {
            viewModel.setShop(it)
        }
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            binding.hashtagLayout.chipGroup.checkedChipIds.forEach {
                val chip = view?.findViewById<Chip>(it)
                Log.e(TAG, chip?.tag.toString())
            }
        }

        binding.pictureIndex1.setOnClickListener {
            pickFromGallery(1)
        }

        binding.pictureIndex2.setOnClickListener {
            pickFromGallery(2)
        }

        binding.pictureIndex3.setOnClickListener {
            pickFromGallery(3)
        }
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                val hashTagList = mutableListOf<Int>()
                binding.hashtagLayout.chipGroup.checkedChipIds.forEach { checkedId ->
                    val chip = view?.findViewById<Chip>(checkedId)
                    val index = chip?.tag.toString().toInt()
                    Log.d(TAG, "해시태그 선택된 index : $index")
                    hashTagList.add(index)
                }
                viewModel.postReview(hashTagList)
            }
        }

        viewModel.successEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                val action =
                        WriteReviewFragmentDirections.actionNavigationWriteReviewToNavigationShopDetail(
                                args?.shop
                        )
                findNavController().navigate(action)
            }
        }
    }

    private fun pickFromGallery(index: Int) {
        val intent = Intent().apply {
            action = Intent.ACTION_PICK
            type = "image/*"
        }
        when (index) {
            1 -> requestGallery1.launch(intent)
            2 -> requestGallery2.launch(intent)
            3 -> requestGallery3.launch(intent)
        }
    }

    private fun setImage(imgUri: Uri, index: Int) {
        when (index) {
            1 -> Glide.with(requireContext()).load(imgUri).into(binding.pictureIndex1)
            2 -> Glide.with(requireContext()).load(imgUri).into(binding.pictureIndex2)
            3 -> Glide.with(requireContext()).load(imgUri).into(binding.pictureIndex3)
        }
    }

    companion object {
        private val TAG: String = WriteReviewFragment::class.java.simpleName
    }
}
