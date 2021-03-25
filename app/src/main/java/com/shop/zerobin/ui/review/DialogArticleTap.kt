package com.shop.zerobin.ui.review

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shop.zerobin.R

class DialogArticleTap : BottomSheetDialogFragment() {

    var viewType: ViewType? = null
    var onClick: ((ClickType) -> Unit)? = null

    enum class ViewType {
        MINE, OTHER
    }

    enum class ClickType {
        REPORT, EDIT, DELETE
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val layoutResId =
                if (viewType == ViewType.OTHER) R.layout.bottomsheet_dialog else R.layout.bottomsheet_dialog_owner
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)

        if (viewType == ViewType.OTHER) { //다른 유저일때
            view.findViewById<TextView>(R.id.btnCancelDialog).setOnClickListener {
                dismiss()
            }
            view.findViewById<TextView>(R.id.btnReportArticle).setOnClickListener {
                onClick?.invoke(ClickType.REPORT)
                dismiss()
            }
        } else {
            //자신일때
            view.findViewById<TextView>(R.id.btnEditArticle).setOnClickListener {
                Toast.makeText(context, "리뷰수정", Toast.LENGTH_SHORT).show()
                onClick?.invoke(ClickType.EDIT)
                dismiss()
            }
            view.findViewById<TextView>(R.id.btnDeleteArticle).setOnClickListener {
                Toast.makeText(context, "리뷰삭제", Toast.LENGTH_SHORT).show()
                onClick?.invoke(ClickType.DELETE)
                dismiss()
            }
            view.findViewById<TextView>(R.id.btnCancelDialog).setOnClickListener {
                dismiss()
            }
        }

        val resources = resources
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val parent = view.parent as View
            val layoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.setMargins(
                    resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                    0,
                    resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                    0
            )
            parent.layoutParams = layoutParams
        }
    }

    // A lot of other things
}