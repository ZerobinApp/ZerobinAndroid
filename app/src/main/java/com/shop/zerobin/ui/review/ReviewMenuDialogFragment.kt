package com.shop.zerobin.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shop.zerobin.R

class ReviewMenuDialogFragment(
    val itemClick: (Boolean) -> Unit
) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottomsheet_menu_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textCancel).setOnClickListener {
            itemClick(false)
            dialog?.dismiss()
        }
        view.findViewById<TextView>(R.id.textModify).setOnClickListener {
            itemClick(true)
            dialog?.dismiss()
        }

    }

    companion object {
        fun newInstance(
            itemClick: (Boolean) -> Unit
        ): ReviewMenuDialogFragment {
            return ReviewMenuDialogFragment(itemClick)
        }
    }
}