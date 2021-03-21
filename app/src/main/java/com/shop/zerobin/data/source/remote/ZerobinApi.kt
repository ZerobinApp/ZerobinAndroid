package com.shop.zerobin.data.source.remote

import com.shop.zerobin.data.source.remote.mypage.*
import com.shop.zerobin.data.source.remote.review.DefaultResponse
import com.shop.zerobin.data.source.remote.review.ReviewListResponse
import com.shop.zerobin.data.source.remote.review.ReviewRequest
import com.shop.zerobin.data.source.remote.shop.*
import retrofit2.http.*

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

    @POST("user")
    suspend fun signUp(@Body body: SignUpRequest): SignUpResponse

    @POST("login")
    suspend fun signIn(@Body body: SignInRequest): SignInResponse

    @PATCH("user/nickname")
    suspend fun nickNameChange(@Body body: MyPageNickNameChangeRequest): MyPageNickNameChangeResponse

    @POST("shop/{shopIndex}/review")
    suspend fun postReview(@Path("shopIndex") shopIndex: Int, @Body body: ReviewRequest): DefaultResponse
}

