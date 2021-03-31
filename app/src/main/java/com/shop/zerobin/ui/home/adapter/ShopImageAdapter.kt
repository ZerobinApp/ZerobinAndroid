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
import com.shop.zerobin.databinding.ItemShopImageBinding
import com.shop.zerobin.util.GlideApp

class ShopImageAdapter : RecyclerView.Adapter<ShopImageAdapter.ShopImageViewHolder>() {

    private var item = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopImageViewHolder {
        val binding =
            ItemShopImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopImageViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(imgUrl: List<String>) {
        item = imgUrl
        notifyDataSetChanged()
    }

    class ShopImageViewHolder(private val binding: ItemShopImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            setImageFromFirebase(imgUrl)
        }

        private fun setImageFromFirebase(imgUrl: String) {
            if (imgUrl.isBlank()) return

            val spaceReference = Firebase.storage.reference.child(imgUrl)
            Log.e(TAG, spaceReference.toString())
            GlideApp.with(binding.shopImage.context)
                .load(spaceReference)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(ContextCompat.getDrawable(binding.shopImage.context, R.drawable.no_image))
                .into(binding.shopImage)
        }
    }

    companion object {
        private val TAG: String = ShopImageAdapter::class.java.simpleName
    }
}