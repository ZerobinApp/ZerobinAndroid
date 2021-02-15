package com.example.zerobin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zerobin.databinding.ItemStoreBinding
import com.example.zerobin.model.Store

class StoreAdapter : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private var item = emptyArray<Store>()
    var onClick: ((Store) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(item[position], onClick)
    }

    override fun getItemCount() = item.size

    fun setItem(arrayOfStores: Array<Store>) {
        item = arrayOfStores
        notifyDataSetChanged()
    }

    class StoreViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store, onClick: ((Store) -> Unit)?) {
            binding.store = store
            binding.onClick = onClick
        }
    }
}