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
import com.shop.zerobin.databinding.ItemShopBinding
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.util.GlideApp

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private var item = emptyList<Shop>()
    var onClick: ((Shop) -> Unit)? = null
    var onZzimClick: ((Shop, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(position, item[position])
    }

    override fun getItemCount() = item.size

    fun setItem(shopList: List<Shop>) {
        item = shopList
        notifyDataSetChanged()
    }

    inner class ShopViewHolder(private val binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, shop: Shop) {
            setImageFromFirebase(shop)

            binding.shop = shop
            binding.onClick = onClick
            binding.onZzimClick = onZzimClick
            binding.position = position
        }

        private fun setImageFromFirebase(shop: Shop) {
            if (shop.imageUrl.isBlank()) return

            val spaceReference = Firebase.storage.reference.child(shop.imageUrl)
            Log.e(TAG, spaceReference.toString())
            GlideApp.with(binding.shopImage.context)
                .load(spaceReference)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(ContextCompat.getDrawable(binding.shopImage.context, R.drawable.no_image))
                .into(binding.shopImage)
        }
    }

    companion object {
        private val TAG: String = ShopAdapter::class.java.simpleName
    }
}