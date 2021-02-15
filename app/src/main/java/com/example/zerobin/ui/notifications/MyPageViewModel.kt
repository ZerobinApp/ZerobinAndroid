package com.example.zerobin.ui.notifications

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.R
import com.example.zerobin.model.Store
import com.example.zerobin.model.User

class MyPageViewModel : ViewModel() {

    /* private val _favoriteStoreCount = MutableLiveData<Int>().apply {
         value = 1
     }
     val text: LiveData<Int> = _favoriteStoreCount
     private val _favoriteReviewCount = MutableLiveData<Int>().apply {
         value = 1
     }
     val text2: LiveData<Int> = _favoriteReviewCount*/

    private val _myUser = MutableLiveData<User>()

    val myUser: LiveData<User> = _myUser

    var favoriteStoreCount: MutableLiveData<Int> = MutableLiveData<Int>()
    var favoriteReviewCount: MutableLiveData<Int> = MutableLiveData<Int>()


    fun nextFavoriteReview() {

    }

    fun requestMyPage() {
        val tempUser1 = User("ny@gamil", "ny", 3, 5)

        _myUser.value = tempUser1
    }


}