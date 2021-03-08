package com.example.zerobin.data.source.remote

import com.example.zerobin.data.source.remote.review.ReviewListResponse
import com.example.zerobin.data.source.remote.shop.ShopDetailResponse
import com.example.zerobin.data.source.remote.shop.ShopListRequest
import com.example.zerobin.data.source.remote.shop.ShopListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ZerobinApi {
    @POST("shop/info")
    suspend fun getShopList(@Body body: ShopListRequest): ShopListResponse

    @GET("review")
    suspend fun getReviewList(): ReviewListResponse

    @GET("shop/{shopIndex}")
    suspend fun getShopDetail(@Path("shopIndex") shopIndex: Int): ShopDetailResponse
}

