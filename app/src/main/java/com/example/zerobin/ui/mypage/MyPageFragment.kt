package com.example.zerobin.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

    private lateinit var myPageViewModel: MyPageViewModel
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myPageViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userVM = myPageViewModel

        binding.nextBtnFavoriteReview.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_my_page_to_navigation_my_page_review)
        }
        binding.nextBtnFavoriteShop.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_my_page_to_navigation_my_page_shop)
        }
        binding.nextBtnFavoriteStamp.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_my_page_to_navigation_my_page_stamp)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestReviewList()

    }

    private fun requestReviewList() {
        myPageViewModel.requestMyPage()
    }
}