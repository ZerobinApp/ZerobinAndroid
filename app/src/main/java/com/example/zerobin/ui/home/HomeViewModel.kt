package com.example.zerobin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Store

class HomeViewModel : ViewModel() {

    private val _storeList = MutableLiveData<Array<Store>>()
    val storeList: LiveData<Array<Store>> = _storeList

    fun requestStoreList() {
        val tempStore1 = Store("알맹상점", "#친환경  #리필스테이션  #비건화장품", true, "서울특별시 마포구 월드컵로 49 2층", "https://image")
        val tempStore2 = Store("허그어웨일", "#친환경  #리필스테이션  #비건화장품", false, "서울특별시 마포구 월드컵로 49 2층", "https://image")
        val tempStore3 = Store("지구샵", "#친환경  #리필스테이션  #비건화장품", false, "서울특별시 마포구 월드컵로 49 2층", "https://image")

        _storeList.value = arrayOf(tempStore1, tempStore2, tempStore3, tempStore1, tempStore2, tempStore3)
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }
}