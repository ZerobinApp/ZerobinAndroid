package com.shop.zerobin.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  BaseBindingFragment.kt
 *
 *  Created by Donghyun An on 2021/03/11
 *  Copyright © 2020 Shinhan Bank. All rights reserved.
 */

abstract class BaseBindingFragment<B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    protected lateinit var binding: B
        private set

    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        initBinding(inflater, container)
        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate<B>(inflater, layoutResId, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    fun showLoading() {
        progressDialog = ProgressDialog(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            progressDialog?.show()
        }
    }

    fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}