package com.example.zerobin.ui.notifications


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Review
import com.example.zerobin.model.User

class MyPageViewModel : ViewModel() {


    private val _myUser = MutableLiveData<User>()

    val myUser: LiveData<User> = _myUser


    fun requestMyPage() {

        val tempReview1 = Review("야호상점", "ㅋㅋㅋ", true, "22229988", "asdfasfasfasdfsdf", "asdfad")
        val tempReview2 = Review("나영상점", "ㅋㅋㅋ", false, "22229988", "asdfasfasfasdfsdf", "asdfad")


        _myUser.value = User("ny@gamil", "ny", 3, 5, arrayListOf(tempReview1, tempReview2))
    }


}