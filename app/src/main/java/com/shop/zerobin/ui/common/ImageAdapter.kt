package com.shop.zerobin.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ItemImageBinding
import com.shop.zerobin.util.GlideApp

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var item = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(imgUrl: List<String>) {
        item = imgUrl
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imgUrl: String) {
            setImageFromFirebase(imgUrl)
        }

        private fun setImageFromFirebase(imgUrl: String) {
            if (imgUrl.isBlank()) return

            val spaceReference = Firebase.storage.reference.child(imgUrl)
            Log.e(TAG, spaceReference.toString())
            GlideApp.with(binding.image.context)
                .load(spaceReference)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(ContextCompat.getDrawable(binding.image.context, R.drawable.no_image))
                .into(binding.image)
        }
    }

    companion object {
        private val TAG: String = ImageAdapter::class.java.simpleName
    }
}