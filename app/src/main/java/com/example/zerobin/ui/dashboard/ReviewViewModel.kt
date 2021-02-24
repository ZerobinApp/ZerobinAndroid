package com.example.zerobin.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Review

class ReviewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _reviewList = MutableLiveData<ArrayList<Review>>()
    val reviewList: LiveData<ArrayList<Review>> = _reviewList


    fun requestReviewList() {
        val tempReview1 = Review(
            "알맹상점",
            "#친환경  #리필스테이션  #비건화장품",
            true,
            "2021년 10월 15일",
            "오늘 처음 방문했는데 사장님이 너무 친절 하셨어요.",
            "https://image"
        )
        val tempReview2 = Review(
            "허그어웨일",
            "#친환경  #리필스테이션  #비건화장품",
            true,
            "2021년 9월 4일",
            "인테리어가 너무 맘에 들어요~~~!!",
            "https"
        )
        val tempReview3 = Review(
            "지구샵",
            "#친환경  #리필스테이션  #비건화장품",
            false,
            "2021년 1월 31일",
            "리필하니까 환경에 도움도 되고 맘이 너무 뿌듯해요",
            "https://image"
        )

        _reviewList.value =
            arrayListOf(
                tempReview1,
                tempReview2,
                tempReview3,
                tempReview1,
                tempReview2,
                tempReview3
            )
    }
}