package com.example.zerobin.ui.common

import androidx.appcompat.app.AppCompatActivity
import com.example.zerobin.ui.common.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    fun showLoading() {
        progressDialog = ProgressDialog(this)
        CoroutineScope(Main).launch {
            delay(1000)
            progressDialog?.show()
        }
    }

    fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}