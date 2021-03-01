package com.example.zerobin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageReviewBinding
import com.example.zerobin.ui.review.adapter.ReviewAdapter


class MyPageReviewFragment : Fragment() {


    private lateinit var binding: FragmentMyPageReviewBinding
    private lateinit var myPageReviewViewModel: MyPageViewModel

    private val reviewAdapter by lazy { ReviewAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        myPageReviewViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_review, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.reviewVM = myPageReviewViewModel


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReviewAdapter()
        observeLiveData()
        requestReviewList()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setReviewAdapter() {
        binding.myPageReviewRecyclerView.adapter = reviewAdapter
    }

    private fun observeLiveData() {
        myPageReviewViewModel.myUser.observe(viewLifecycleOwner) {
            reviewAdapter.setItem(it.ReviewList)
        }
    }

    private fun requestReviewList() {
        myPageReviewViewModel.requestMyPage()
    }

}