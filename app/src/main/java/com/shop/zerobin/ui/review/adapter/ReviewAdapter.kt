package com.shop.zerobin.ui.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shop.zerobin.databinding.ItemReviewBinding
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.ShopDetail

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
        fun onMenuClick()
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
                listener?.onMenuClick()
            }
        }
    }


}