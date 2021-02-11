package com.example.zerobin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zerobin.R

class MyPageFragment : Fragment() {

    private lateinit var myPageViewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageViewModel =
            ViewModelProvider(this).get(MypageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mypage, container, false)
        val textView: TextView = root.findViewById(R.id.text_my_page)
        myPageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}