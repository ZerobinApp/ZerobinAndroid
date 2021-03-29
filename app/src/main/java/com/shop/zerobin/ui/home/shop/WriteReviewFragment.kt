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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentWriteReviewBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class WriteReviewFragment :
    BaseBindingFragment<FragmentWriteReviewBinding>(R.layout.fragment_write_review) {

    private val args: WriteReviewFragmentArgs? by navArgs()

    private val viewModel: WriteReviewViewModel by viewModel()

    private val uploadState = hashMapOf<Uri, Boolean>()

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
        viewModel.setHashTagList()
        if (args?.seed == true) viewModel.bloomSeed()
        args?.shop?.let {
            viewModel.setShop(it)
        }
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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

        viewModel.hashTagList.observe(viewLifecycleOwner) { hashTagList ->
            for (i in 0 until binding.hashTagContainer.chipGroup.childCount) {
                view?.findViewWithTag<Chip>("${hashTagList[i].hashtagIndex}")?.text =
                    getString(R.string.hash_tag_format, hashTagList[i].name)
            }
        }

        viewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                uploadImage()
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
            1 -> {
                Glide.with(requireContext()).load(imgUri).into(binding.pictureIndex1)
                binding.pictureIndex1.tag = imgUri
            }
            2 -> {
                Glide.with(requireContext()).load(imgUri).into(binding.pictureIndex2)
                binding.pictureIndex2.tag = imgUri
            }
            3 -> {
                Glide.with(requireContext()).load(imgUri).into(binding.pictureIndex3)
                binding.pictureIndex3.tag = imgUri
            }
        }
    }

    private fun uploadImage() {
        showLoading()
        val imgUri1 = binding.pictureIndex1.tag as? Uri
        val imgUri2 = binding.pictureIndex2.tag as? Uri
        val imgUri3 = binding.pictureIndex3.tag as? Uri

        if (imgUri1 == null && imgUri2 == null && imgUri3 == null) {
            postReview()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            uploadImageToFirebase(imgUri1 ?: return@launch)
            uploadImageToFirebase(imgUri2 ?: return@launch)
            uploadImageToFirebase(imgUri3 ?: return@launch)
        }
    }

    private fun uploadImageToFirebase(imgUri: Uri) {
        uploadState[imgUri] = false
        val fileName = "${Firebase.auth.currentUser?.email}_${imgUri.lastPathSegment}"
        val reviewRef = Firebase.storage.reference.child("reviews/${fileName}")

        val uploadTask = reviewRef.putFile(imgUri)
        uploadTask.addOnSuccessListener {
            Log.d(TAG, "uploadImage > onSuccess : $it")
            viewModel.addImageUrl(reviewRef.path)
            uploadState[imgUri] = true
            postReview()
        }.addOnFailureListener {
            Log.d(TAG, "uploadImage > onFailure : $it")
            Toast.makeText(requireContext(), "사진 업로드 실패 ${it.message}", Toast.LENGTH_SHORT).show()
            hideLoading()
        }
    }

    private fun postReview() {
        uploadState.forEach { (_, finish) ->
            if (!finish) return
        }

        val selectedHashTagList = mutableListOf<Int>()
        binding.hashTagContainer.chipGroup.checkedChipIds.forEach { checkedId ->
            val chip = view?.findViewById<Chip>(checkedId)
            val index = chip?.tag.toString().toInt()
            Log.d(TAG, "해시태그 선택된 index : $index")
            selectedHashTagList.add(index)
        }
        if (selectedHashTagList.size > 8) {
            Toast.makeText(requireContext(), "해시태그는 최대 8개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.postReview(selectedHashTagList)
    }

    companion object {
        private val TAG: String = WriteReviewFragment::class.java.simpleName
    }
}
