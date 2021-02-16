package com.example.zerobin.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageStampBinding
import com.example.zerobin.model.Review
import com.example.zerobin.ui.dashboard.adapter.ReviewAdapter



class MyPageStampFragment : Fragment() {


    private lateinit var binding: FragmentMyPageStampBinding
    private lateinit var homeViewModel: MyPageViewModel

    private val myPageStampAdapter by lazy { ReviewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeViewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_stamp, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.reviewVM = homeViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStoreAdapter()
        observeLiveData()
        requestStoreList()
        //setListener()
    }

    private fun setStoreAdapter() {
        binding.myPageStampRecyclerView.adapter = myPageStampAdapter
    }

    private fun observeLiveData() {
        homeViewModel.myUser.observe(viewLifecycleOwner) {
            val item = ArrayList<Review>()
            for (i in 0 until it.ReviewList.size) {
                if (it.ReviewList[i].favorite) {

                    item.add(it.ReviewList[i])
                }
            }
            myPageStampAdapter.setItem(item)

        }
    }

    private fun requestStoreList() {
        homeViewModel.requestMyPage()
    }

/*    private fun setListener() {
        myPageStampAdapter.onClick = { store ->
            val intent = Intent(requireContext(), StoreDetailActivity::class.java).apply {
                putExtra(StoreDetailActivity.EXTRA_STORE, store)
            }
            startActivity(intent)
        }
    }*/

}