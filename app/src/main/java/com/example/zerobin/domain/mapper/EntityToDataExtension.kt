package com.example.zerobin.domain.mapper

import com.example.zerobin.data.source.remote.shop.ShopListRequest

object EntityToDataExtension {
    fun shopListEntityToData(type: List<Int>) = ShopListRequest(type)
}