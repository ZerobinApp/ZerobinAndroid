package com.shop.zerobin.data.source.remote

import com.shop.zerobin.data.source.remote.mypage.*
import com.shop.zerobin.data.source.remote.review.*
import com.shop.zerobin.data.source.remote.shop.HashtagListResponse
import com.shop.zerobin.data.source.remote.shop.ShopDetailResponse
import com.shop.zerobin.data.source.remote.shop.ShopListRequest
import com.shop.zerobin.data.source.remote.shop.ShopListResponse
import retrofit2.http.*

interface ZerobinApi {
    @POST("shop/info")
    suspend fun getShopList(@Body body: ShopListRequest): ShopListResponse

    @POST("review")
    suspend fun getReviewList(@Body body: ReviewListRequest): ReviewListResponse

    @GET("shop/{shopIndex}")
    suspend fun getShopDetail(@Path("shopIndex") shopIndex: Int): ShopDetailResponse

    @GET("user/shop/like")
    suspend fun getMyPageShop(): MyPageShopResponse

    @GET("user/review")
    suspend fun getMyPageReview(): MyPageReviewResponse

    @GET("user/info")
    suspend fun getMyPageUser(): UserResponse

    @GET("user/shop/stamp")
    suspend fun getMyPageStamp(): MyPageStampResponse

    @POST("user")
    suspend fun signUp(@Body body: SignUpRequest): SignUpResponse

    @POST("login")
    suspend fun signIn(@Body body: SignInRequest): SignInResponse

    @PATCH("user/nickname")
    suspend fun nickNameChange(@Body body: MyPageNickNameChangeRequest): MyPageNickNameChangeResponse

    @POST("shop/{shopIndex}/review")
    suspend fun postReview(
        @Path("shopIndex") shopIndex: Int,
        @Body body: ReviewRequest,
    ): DefaultResponse

    @GET("shop/info")
    suspend fun searchShop(
        @Query("name") shopName: String,
    ): ShopListResponse

    @GET("hashtag")
    suspend fun getHashtag(): HashtagListResponse

    @POST("review/{reviewIndex}/report")
    suspend fun reportReview(
        @Path("reviewIndex") reviewIndex: Int,
    ): DefaultResponse

    @DELETE("shop/{shopIndex}/review/{reviewIndex}")
    suspend fun deleteReview(
        @Path("shopIndex") shopIndex: Int,
        @Path("reviewIndex") reviewIndex: Int
    ): DefaultResponse

    @PATCH("/shop/{shopIndex}/like")
    suspend fun zzimShop(@Path("shopIndex") shopIndex: Int): DefaultResponse


    @DELETE("user")
    suspend fun deleteUser() : DefaultResponse

    @GET("shop/{shopIndex}/review/{reviewIndex}")
    suspend fun getReviewDetail(
        @Path("shopIndex") shopIndex: Int,
        @Path("reviewIndex") reviewIndex: Int
    ):ReviewDetailResponse

    @PATCH("shop/{shopIndex}/review/{reviewIndex}")
    suspend fun setReviewDetail(
        @Path("shopIndex") shopIndex: Int,
        @Path("reviewIndex") reviewIndex: Int,
        @Body body: ReviewModifyRequest
    ):DefaultResponse
}

