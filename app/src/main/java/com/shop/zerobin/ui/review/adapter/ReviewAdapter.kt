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
import com.shop.zerobin.databinding.ItemReviewBinding
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.util.Extensions.px
import com.shop.zerobin.util.GlideApp

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    private var item = emptyList<Review>()
    var onClick: ((Review) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(arrayOfReviews: List<Review>) {
        item = arrayOfReviews
        notifyDataSetChanged()
    }

    fun setDetailReviewItem(arrayOfShop: ShopDetail) {
        item = arrayOfShop.reviewList
        notifyDataSetChanged()
    }

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(review: Review)
        fun onMenuClick(review: Review)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ReviewHolder(private val binding: ItemReviewBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.review = review
            binding.shopName.setOnClickListener {
                listener?.onItemClick(review = review)
            }
            binding.reviewMenu.setOnClickListener {
                listener?.onMenuClick(review = review)
            }

            setImageFromFirebase(review)
        }

        private fun setImageFromFirebase(review: Review) {
            if (review.imageList.isEmpty()) {
                GlideApp.with(binding.reviewImg.context)
                        .load(ContextCompat.getDrawable(binding.reviewImg.context, R.drawable.no_image))
                        .transform(CenterCrop(), RoundedCorners(20.px))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.reviewImg)
            } else {
                val spaceReference = Firebase.storage.reference.child(review.imageList[0])
                Log.e(TAG, spaceReference.toString())
                GlideApp.with(binding.reviewImg.context)
                        .load(spaceReference)
                        .error(
                                ContextCompat.getDrawable(
                                        binding.reviewImg.context,
                                        R.drawable.no_image
                                )
                        )
                        .transform(CenterCrop(), RoundedCorners(20.px))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.reviewImg)
            }
        }
    }

    companion object {
        private val TAG: String = ReviewAdapter::class.java.simpleName
    }
}