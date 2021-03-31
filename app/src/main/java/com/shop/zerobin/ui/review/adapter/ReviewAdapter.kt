package com.shop.zerobin.ui.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shop.zerobin.R
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
        fun onMenuClick(review: Review)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ReviewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            setReviewImageAdapter(review.imageList)
            drawHashTagList(review.hashtagList)

            binding.review = review
            binding.shopName.setOnClickListener {
                listener?.onItemClick(review = review)
            }
            binding.reviewMenu.setOnClickListener {
                listener?.onMenuClick(review = review)
            }
        }

        private fun setReviewImageAdapter(imageList: List<String>) {
            val filteredImageList = imageList.filter { it.isNotBlank() }
            binding.reviewViewPager.isVisible = filteredImageList.isNotEmpty()

            if (filteredImageList.isEmpty()) return

            val reviewImageAdapter = ReviewImageAdapter()
            binding.reviewViewPager.adapter = reviewImageAdapter
            val onPageSelectedCallback = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val pageText = binding.root.context.getString(R.string.review_image_page_format,
                        position + 1,
                        filteredImageList.size)
                    binding.page.text = pageText
                }
            }
            binding.reviewViewPager.registerOnPageChangeCallback(onPageSelectedCallback)
            reviewImageAdapter.setItem(filteredImageList)

            onPageSelectedCallback.onPageSelected(0)
        }

        private fun drawHashTagList(hashTagList: List<String>) {
            var hashTagString = ""
            hashTagList.forEach {
                hashTagString += binding.root.context.getString(R.string.hash_tag_format, it) + "  "
            }
            binding.reviewHashTag.text = hashTagString
        }
    }

    companion object {
        private val TAG: String = ReviewAdapter::class.java.simpleName
    }
}