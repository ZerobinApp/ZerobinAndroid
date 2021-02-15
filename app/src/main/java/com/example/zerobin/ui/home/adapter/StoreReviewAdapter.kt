package com.example.zerobin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zerobin.databinding.ItemReviewBinding
import com.example.zerobin.model.Review
import com.example.zerobin.model.Store
import com.example.zerobin.model.StoreDetail

class StoreReviewAdapter : RecyclerView.Adapter<StoreReviewAdapter.StoreReviewHolder>() {

    private var item = emptyArray<Review>()
    var onClick: ((Store) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreReviewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreReviewHolder, position: Int) {
        holder.bind(item[position], position)
    }

    override fun getItemCount() = item.size

    fun setItem(arrayOfStores: StoreDetail) {
        item = arrayOfStores.reviewList.toTypedArray()
        notifyDataSetChanged()
    }

    class StoreReviewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review, position: Int) {
            binding.review = review
        }
    }
}