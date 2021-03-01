package com.example.zerobin.ui.notifications


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Review
import com.example.zerobin.model.Shop
import com.example.zerobin.model.User

class MyPageViewModel : ViewModel() {


    private val _myUser = MutableLiveData<User>()

    val myUser: LiveData<User> = _myUser

    fun requestMyPage() {

        val tempReview1 = Review("야호상점", "ㅋㅋㅋ", true, "22229988", "asdfasfasfasdfsdf", "asdfad")
        val tempReview2 = Review("나영상점", "ㅋㅋㅋ", false, "22229988", "asdfasfasfasdfsdf", "asdfad")
        val listReview: ArrayList<Review> = arrayListOf(tempReview1, tempReview2)
        val tempShop1 =
            Shop("알맹상점", "#친환경  #리필스테이션  #비건화장품", true, "서울특별시 마포구 월드컵로 49 2층", "https://image")
        val tempShop2 =
            Shop("알맹상점", "#친환경  #리필스테이션  #비건화장품", true, "서울특별시 마포구 월드컵로 49 2층", "https://image")
        val listShop: ArrayList<Shop> = arrayListOf(tempShop1, tempShop2)

        _myUser.value = User(
            "ny@gamil", "ny", listShop.size, listReview.size, listReview,
            listShop
        )
    }


}