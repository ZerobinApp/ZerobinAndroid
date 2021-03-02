package com.example.zerobin.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.shop.ShopRepository
import com.example.zerobin.domain.entity.Shop
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val shopRepository = ShopRepository()

    private val _hashtagList = MutableLiveData<String>()
    val hashtagList: LiveData<String> = _hashtagList

    private val _shopList = MutableLiveData<List<Shop>>()
    val shopList: LiveData<List<Shop>> = _shopList

    fun requestShopList() {
        viewModelScope.launch {
            val data = shopRepository.getShopList(listOf(1,2))
            Log.d(TAG, data.toString())
            var hashTagList = ""
            data.first.forEach {
                hashTagList += "#$it  "
            }
            _hashtagList.value = hashTagList
            _shopList.value = data.second
        }

    }

    companion object {
        val TAG: String = HomeViewModel::class.java.simpleName
    }
}