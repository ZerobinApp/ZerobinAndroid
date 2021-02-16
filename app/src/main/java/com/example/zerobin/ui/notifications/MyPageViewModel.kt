package com.example.zerobin.ui.notifications


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.User

class MyPageViewModel : ViewModel() {


    private val _myUser = MutableLiveData<User>()

    val myUser: LiveData<User> = _myUser


    fun requestMyPage() {
        val tempUser1 = User("ny@gamil", "ny", 3, 5)

        _myUser.value = tempUser1
    }


}