package com.example.zerobin.ui.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zerobin.databinding.ItemReviewBinding
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.entity.ShopDetail

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    private var item = ArrayList<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(arrayOfReviews: ArrayList<Review>) {
        item = arrayOfReviews
        notifyDataSetChanged()
    }

    fun setDetailReviewItem(arrayOfShop: ShopDetail) {
        item = arrayOfShop.reviewList
        notifyDataSetChanged()
    }

    class ReviewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.review = review
        }
    }
}