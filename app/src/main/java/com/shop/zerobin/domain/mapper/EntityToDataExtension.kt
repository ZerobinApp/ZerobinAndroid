package com.shop.zerobin.domain.mapper

import com.shop.zerobin.data.source.remote.mypage.MyPageNickNameChangeRequest
import com.shop.zerobin.data.source.remote.mypage.SignInRequest
import com.shop.zerobin.data.source.remote.mypage.SignUpRequest
import com.shop.zerobin.data.source.remote.shop.ShopListRequest

object EntityToDataExtension {
    fun shopListEntityToData(type: List<Int>) = ShopListRequest(type)

    fun signUpEntityToData(email: String, password: String, nickname: String) = SignUpRequest(email, password, nickname)

    fun signInEntityToData(email: String, password: String) = SignInRequest(email, password)

    fun nickNameChangeEntityToData(nickname: String)=MyPageNickNameChangeRequest(nickname)
}