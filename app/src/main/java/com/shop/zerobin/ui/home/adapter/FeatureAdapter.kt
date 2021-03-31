package com.shop.zerobin.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ItemFeatureBinding
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.util.GlideApp

class FeatureAdapter : RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {

    private var item = emptyList<ShopDetail.Hashtag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val binding = ItemFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(hashTagList: List<ShopDetail.Hashtag>) {
        item = hashTagList
        notifyDataSetChanged()
    }

    class FeatureViewHolder(private val binding: ItemFeatureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hashTag: ShopDetail.Hashtag) {
            setImageFromFirebase(hashTag.image)

            binding.hashTag = hashTag
        }

        private fun setImageFromFirebase(imageUrl: String) {
            if (imageUrl.isBlank()) return

            val spaceReference = Firebase.storage.reference.child(imageUrl)
            Log.e(TAG, spaceReference.toString())
            GlideApp.with(binding.root.context)
                .load(spaceReference)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.no_image))
                .into(binding.featureImage)
        }
    }

    companion object {
        private val TAG: String = FeatureAdapter::class.java.simpleName
    }
}