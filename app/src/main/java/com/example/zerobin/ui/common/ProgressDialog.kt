package com.example.zerobin.ui.common

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.zerobin.R

class ProgressDialog(context: Context) : Dialog(context) {

    init {
        window?.setBackgroundDrawableResource(R.color.transparent)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
    }

    override fun onBackPressed() {
        dismiss()
        super.onBackPressed()
    }
}