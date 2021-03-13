package com.shop.zerobin.domain.mapper

import com.shop.zerobin.data.source.remote.shop.ShopListRequest

object EntityToDataExtension {
    fun shopListEntityToData(type: List<Int>) = ShopListRequest(type)
}