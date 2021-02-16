package com.example.zerobin.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentReviewBinding
import com.example.zerobin.ui.dashboard.adapter.ReviewAdapter


class ReviewFragment : Fragment() {

    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var binding: FragmentReviewBinding

    private val reviewAdapter by lazy { ReviewAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewViewModel =
            ViewModelProvider(this).get(ReviewViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.reviewVM = reviewViewModel


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
        binding.reviewRecyclerView.adapter = reviewAdapter
    }

    private fun observeLiveData() {
        reviewViewModel.reviewList.observe(viewLifecycleOwner) {
            reviewAdapter.setItem(it)
        }
    }

    private fun requestReviewList() {
        reviewViewModel.requestReviewList()
    }

}