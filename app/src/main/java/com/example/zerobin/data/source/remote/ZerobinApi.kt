package com.example.zerobin.data.source.remote

import com.example.zerobin.data.source.remote.Mypage.MypageReviewResponse
import com.example.zerobin.data.source.remote.Mypage.MypageShopResponse
import com.example.zerobin.data.source.remote.Mypage.MypageStampResponse
import com.example.zerobin.data.source.remote.User.UserResponse
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

    @GET("user/shop/like")
    suspend fun getMypageShop(): MypageShopResponse

    @GET("user/review")
    suspend fun getMypageReview(): MypageReviewResponse

    @GET("user/info")
    suspend fun getMypageUser(): UserResponse

    @GET("user/review/stamp")
    suspend fun getMypageStamp(): MypageStampResponse
}

