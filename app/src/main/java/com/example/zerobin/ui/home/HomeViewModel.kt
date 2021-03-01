package com.example.zerobin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Shop

class HomeViewModel : ViewModel() {

    private val _shopList = MutableLiveData<ArrayList<Shop>>()
    val shopList: LiveData<ArrayList<Shop>> = _shopList

    fun requestShopList() {
        val tempShop1 =
            Shop("알맹상점", "#친환경  #리필스테이션  #비건화장품", true, "서울특별시 마포구 월드컵로 49 2층", "https://image")
        val tempShop2 =
            Shop("허그어웨일", "#친환경  #리필스테이션  #비건화장품", true, "서울특별시 마포구 월드컵로 49 2층", "https://image")
        val tempShop3 =
            Shop("지구샵", "#친환경  #리필스테이션  #비건화장품", false, "서울특별시 마포구 월드컵로 49 2층", "https://image")

        _shopList.value =
            arrayListOf(tempShop1, tempShop2, tempShop3, tempShop1, tempShop2, tempShop3)
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }
}