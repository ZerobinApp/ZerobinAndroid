package com.shop.zerobin.ui.review

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shop.zerobin.R
import org.koin.android.viewmodel.ext.android.viewModel

class DialogArticleTap(
    @LayoutRes
    private val layoutResId: Int, private val reviewIndex: Int
) : BottomSheetDialogFragment() {

    private val reviewFilterViewModel: ReviewViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layoutResId, container, false)
        Log.d("레이아웃", layoutResId.toString())
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)

        if (layoutResId == 2131492895) { //다른 유저일때
            requireView().findViewById<TextView>(R.id.btnCancelDialog).setOnClickListener {
                Toast.makeText(context, "리뷰취소", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            requireView().findViewById<TextView>(R.id.btnReportArticle).setOnClickListener {
                Toast.makeText(context, "리뷰신고", Toast.LENGTH_SHORT).show()
                reviewFilterViewModel.requestReviewReport(reviewIndex = reviewIndex)
            }
        } else {
            //자신일때
            requireView().findViewById<TextView>(R.id.btnEditArticle).setOnClickListener {
                Toast.makeText(context, "리뷰수정", Toast.LENGTH_SHORT).show()
            }
            requireView().findViewById<TextView>(R.id.btnDeleteArticle).setOnClickListener {
                Toast.makeText(context, "리뷰삭제", Toast.LENGTH_SHORT).show()
            }
            requireView().findViewById<TextView>(R.id.btnCancelDialog).setOnClickListener {
                Toast.makeText(context, "리뷰취소", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        val resources = resources
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val parent = view?.parent as View
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