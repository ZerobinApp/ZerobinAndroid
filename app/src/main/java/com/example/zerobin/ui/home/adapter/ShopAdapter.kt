package com.example.zerobin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zerobin.databinding.ItemShopBinding
import com.example.zerobin.model.Shop

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private var item = emptyList<Shop>()
    var onClick: ((Shop) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(item[position], onClick)
    }

    override fun getItemCount() = item.size

    fun setItem(shopList: List<Shop>) {
        item = shopList
        notifyDataSetChanged()
    }

    class ShopViewHolder(private val binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: Shop, onClick: ((Shop) -> Unit)?) {
            binding.shop = shop
            binding.onClick = onClick
        }
    }
}