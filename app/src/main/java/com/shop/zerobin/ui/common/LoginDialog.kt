package com.shop.zerobin.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.shop.zerobin.R
import com.shop.zerobin.databinding.DialogLoginBinding

class LoginDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    var onClickYes: (() -> Unit)? = null

    private lateinit var binding: DialogLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable())
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.dialog_login,
            null,
            false)

        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            dismiss()
            onClickYes?.invoke()
        }
    }
}