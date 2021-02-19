package com.example.zerobin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
            setFragment(MyPageReviewFragment())
        }
        binding.nextBtnFavoriteStore.setOnClickListener {
            setFragment(MyPageStoreFragment())
        }
        binding.nextBtnFavoriteStamp.setOnClickListener {
            setFragment(MyPageStampFragment())
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


    private fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}