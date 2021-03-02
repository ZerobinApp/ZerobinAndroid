package com.example.zerobin.data.source.remote

import com.example.zerobin.data.source.remote.shop.ShopListRequest
import com.example.zerobin.data.source.remote.shop.ShopListResult
import retrofit2.http.Body
import retrofit2.http.POST

interface ZerobinApi {
    @POST("shop/info")
    suspend fun getShopList(@Body body: ShopListRequest): ShopListResult
}

