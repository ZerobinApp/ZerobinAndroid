package com.shop.zerobin.ui.review.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ItemReviewImageBinding
import com.shop.zerobin.util.Extensions.px
import com.shop.zerobin.util.GlideApp

class ReviewImageAdapter : RecyclerView.Adapter<ReviewImageAdapter.ReviewImageViewHolder>() {

    private var item = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewImageViewHolder {
        val binding =
            ItemReviewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewImageViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(imgUrl: List<String>) {
        item = imgUrl
        notifyDataSetChanged()
    }

    class ReviewImageViewHolder(private val binding: ItemReviewImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            setImageFromFirebase(imgUrl)
        }

        private fun setImageFromFirebase(imgUrl: String) {
            val spaceReference = Firebase.storage.reference.child(imgUrl)
            Log.e(TAG, spaceReference.toString())
            GlideApp.with(binding.reviewImage.context)
                .load(spaceReference)
                .transform(CenterCrop(), RoundedCorners(20.px))
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(ContextCompat.getDrawable(binding.reviewImage.context, R.drawable.no_image))
                .into(binding.reviewImage)
        }
    }

    companion object {
        private val TAG: String = ReviewImageAdapter::class.java.simpleName
    }
}