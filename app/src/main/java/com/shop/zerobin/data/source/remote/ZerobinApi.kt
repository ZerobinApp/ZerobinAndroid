package com.shop.zerobin.data.source.remote

import com.shop.zerobin.data.source.remote.mypage.MyPageReviewResponse
import com.shop.zerobin.data.source.remote.mypage.MyPageShopResponse
import com.shop.zerobin.data.source.remote.mypage.MyPageStampResponse
import com.shop.zerobin.data.source.remote.mypage.UserResponse
import com.shop.zerobin.data.source.remote.review.ReviewListResponse
import com.shop.zerobin.data.source.remote.shop.ShopDetailResponse
import com.shop.zerobin.data.source.remote.shop.ShopListRequest
import com.shop.zerobin.data.source.remote.shop.ShopListResponse
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
    suspend fun getMyPageShop(): MyPageShopResponse

    @GET("user/review")
    suspend fun getMyPageReview(): MyPageReviewResponse

    @GET("user/info")
    suspend fun getMyPageUser(): UserResponse

    @GET("user/review/stamp")
    suspend fun getMyPageStamp(): MyPageStampResponse
}

