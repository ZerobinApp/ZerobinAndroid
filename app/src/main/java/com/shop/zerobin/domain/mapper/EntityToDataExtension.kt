package com.shop.zerobin.domain.mapper

import com.shop.zerobin.data.source.remote.mypage.MyPageNickNameChangeRequest
import com.shop.zerobin.data.source.remote.mypage.SignInRequest
import com.shop.zerobin.data.source.remote.mypage.SignUpRequest
import com.shop.zerobin.data.source.remote.review.ReviewListRequest
import com.shop.zerobin.data.source.remote.review.ReviewModifyRequest
import com.shop.zerobin.data.source.remote.review.ReviewRequest
import com.shop.zerobin.data.source.remote.shop.ShopListRequest

object EntityToDataExtension {
    fun shopListEntityToData(type: List<Int>) = ShopListRequest(type)

    fun reviewListEntityToData(type: List<Int>) = ReviewListRequest(type)

    fun signUpEntityToData(email: String, password: String, nickname: String) =
        SignUpRequest(email, password, nickname)

    fun signInEntityToData(email: String, password: String) = SignInRequest(email, password)

    fun nickNameChangeEntityToData(nickname: String) = MyPageNickNameChangeRequest(nickname)

    fun postReviewEntityToData(
        imageUrlList: List<String>?,
        inputText: String?,
        hashTagList: List<Int>?,
        stamp: Boolean,
    ) = ReviewRequest(
        image = imageUrlList ?: emptyList(),
        comment = inputText ?: "",
        hashtag = hashTagList ?: emptyList(),
        stamp = if (stamp) 1 else 0
    )

    fun setReviewEntityToData(
        comment:String,
        deletehashtag:List<Int>,
        updatehashtag:List<Int>,
        deleteimage:List<String>,
        updateimage:List<String>,
        stamp: Int,
    )= ReviewModifyRequest(
        comment=comment,
        deletehashtag = deletehashtag ?: emptyList(),
        updatehashtag = updatehashtag,
        deleteimage = deleteimage,
        updateimage = updateimage,
        stamp = stamp,
    )
}