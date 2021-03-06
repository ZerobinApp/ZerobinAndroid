package com.shop.zerobin.ui.home.shop

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentWriteReviewBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.util.Extensions.px
import com.shop.zerobin.util.GlideApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream


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

        requestGetReviewIfNeed()
        setViewModelData()
        setOnClickListener()
        observeLiveData()
    }

    private fun requestGetReviewIfNeed() {
        val shopIndex = arguments?.getIntegerArrayList("list")?.get(0)
        val reviewIndex = arguments?.getIntegerArrayList("list")?.get(1)

        if (shopIndex != null && reviewIndex != null) {
            viewModel.requestGetReview(shopIndex, reviewIndex)
        }
    }

    private fun setViewModelData() {
        viewModel.setHashTagList()
        args?.seed?.let {
            viewModel.setSeed(it)
        }
        args?.shopIndex?.let {
            if (it != 0) {
                viewModel.setShopIndex(it)
            }
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

        binding.pictureCancel1.setOnClickListener {
            viewModel.minusImageCount()
            binding.pictureCancel1.isVisible = false
            binding.pictureIndex1.setImageResource(R.drawable.ic_select_picture)
            binding.pictureIndex1.tag = null
            viewModel.reviewDetail.value?.imageList?.get(0)?.let { imgUrl ->
                viewModel.addDeleteImage(imgUrl)
            }
        }

        binding.pictureCancel2.setOnClickListener {
            viewModel.minusImageCount()
            binding.pictureCancel2.isVisible = false
            binding.pictureIndex2.setImageResource(R.drawable.ic_select_picture)
            binding.pictureIndex2.tag = null
            viewModel.reviewDetail.value?.imageList?.get(1)?.let { imgUrl ->
                viewModel.addDeleteImage(imgUrl)
            }
        }

        binding.pictureCancel3.setOnClickListener {
            viewModel.minusImageCount()
            binding.pictureCancel3.isVisible = false
            binding.pictureIndex3.setImageResource(R.drawable.ic_select_picture)
            binding.pictureIndex3.tag = null
            viewModel.reviewDetail.value?.imageList?.get(2)?.let { imgUrl ->
                viewModel.addDeleteImage(imgUrl)
            }
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
                if (viewModel.isModifyState.value == true) {
                    findNavController().popBackStack()
                } else {
                    val action =
                        WriteReviewFragmentDirections
                            .actionNavigationWriteReviewToNavigationShopDetail(args?.shopIndex ?: 0)
                    findNavController().navigate(action)
                }
            }
        }

        viewModel.reviewDetail.observe(viewLifecycleOwner) {
            it.hashTagList.forEach { hashTag ->
                view?.findViewWithTag<Chip>("${hashTag.hashtagIndex}")?.isChecked = true
            }
            it.imageList.forEachIndexed { index, imgUrl ->
                setImageFromFirebase(index, imgUrl)
            }
        }
    }

    private fun setImageFromFirebase(index: Int, imgUrl: String) {
        val spaceReference = Firebase.storage.reference.child(imgUrl)
        when (index) {
            0 -> {
                GlideApp.with(requireContext())
                    .load(spaceReference)
                    .transform(CenterCrop(), RoundedCorners(15.px))
                    .into(binding.pictureIndex1)
                binding.pictureCancel1.isVisible = true
            }
            1 -> {
                GlideApp.with(requireContext())
                    .load(spaceReference)
                    .transform(CenterCrop(), RoundedCorners(15.px))
                    .into(binding.pictureIndex2)
                binding.pictureCancel2.isVisible = true
            }
            2 -> {
                GlideApp.with(requireContext())
                    .load(spaceReference)
                    .transform(CenterCrop(), RoundedCorners(15.px))
                    .into(binding.pictureIndex3)
                binding.pictureCancel3.isVisible = true
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
        viewModel.plusImageCount()
        when (index) {
            1 -> {
                Glide.with(requireContext())
                    .load(imgUri)
                    .transform(CenterCrop(), RoundedCorners(15.px))
                    .into(binding.pictureIndex1)
                binding.pictureCancel1.isVisible = true
                binding.pictureIndex1.tag = imgUri
            }
            2 -> {
                Glide.with(requireContext())
                    .load(imgUri)
                    .transform(CenterCrop(), RoundedCorners(15.px))
                    .into(binding.pictureIndex2)
                binding.pictureCancel2.isVisible = true
                binding.pictureIndex2.tag = imgUri
            }
            3 -> {
                Glide.with(requireContext())
                    .load(imgUri)
                    .transform(CenterCrop(), RoundedCorners(15.px))
                    .into(binding.pictureIndex3)
                binding.pictureCancel3.isVisible = true
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
            imgUri1?.let { uploadImageToFirebase(it) }
            imgUri2?.let { uploadImageToFirebase(it) }
            imgUri3?.let { uploadImageToFirebase(it) }
        }
    }

    private fun uploadImageToFirebase(imgUri: Uri) {
        uploadState[imgUri] = false
        val fileName = "${Firebase.auth.currentUser?.email}_${imgUri.lastPathSegment}"
        val reviewRef = Firebase.storage.reference.child("reviews/${fileName}")

        val compressedImage = compressImage(imgUri)

        val uploadTask = reviewRef.putBytes(compressedImage)
        uploadTask.addOnSuccessListener {
            Log.d(TAG, "uploadImage > onSuccess : $it")
            viewModel.addImageUrl(reviewRef.path.substring(1)) // 경로의 맨 앞에 "/" 지우기 위해 substring(1)
            uploadState[imgUri] = true
            postReview()
        }.addOnFailureListener {
            Log.d(TAG, "uploadImage > onFailure : $it")
            Toast.makeText(requireContext(), "사진 업로드 실패 ${it.message}", Toast.LENGTH_SHORT).show()
            hideLoading()
        }
    }

    private fun compressImage(imgUri: Uri): ByteArray {
        var imageStream: InputStream? = null
        try {
            imageStream = requireContext().contentResolver.openInputStream(imgUri)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, e.message ?: "")
        }

        val bmp = BitmapFactory.decodeStream(imageStream)

        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, stream)
        val byteArray: ByteArray = stream.toByteArray()
        try {
            stream.close()
        } catch (e: IOException) {
            Log.e(TAG, e.message ?: "")
        }
        return byteArray
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
            hideLoading()
            return
        }
        if (viewModel.isModifyState.value == true) {
            viewModel.requestModifyReview(selectedHashTagList)
        } else {
            viewModel.postReview(selectedHashTagList)
        }
    }

    companion object {
        private val TAG: String = WriteReviewFragment::class.java.simpleName
    }
}
