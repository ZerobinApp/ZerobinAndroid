package com.example.zerobin.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageStampBinding
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.ui.review.adapter.ReviewAdapter


class MyPageStampFragment : Fragment() {


    private lateinit var binding: FragmentMyPageStampBinding
    private lateinit var myPageViewModel: MyPageViewModel

    private val myPageStampAdapter by lazy { ReviewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        myPageViewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_stamp, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.reviewVM = myPageViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setShopAdapter()
        observeLiveData()
        requestShopList()
        //setListener()
    }

    private fun setShopAdapter() {
        binding.myPageStampRecyclerView.adapter = myPageStampAdapter
    }

    private fun observeLiveData() {
        myPageViewModel.myUser.observe(viewLifecycleOwner) {
            val item = ArrayList<Review>()
            for (i in 0 until it.reviewList.size) {
                if (it.reviewList[i].stamp) {

                    item.add(it.reviewList[i])
                }
            }
            myPageStampAdapter.setItem(item)

        }
    }

    private fun requestShopList() {
        myPageViewModel.requestMyPage()
    }


}