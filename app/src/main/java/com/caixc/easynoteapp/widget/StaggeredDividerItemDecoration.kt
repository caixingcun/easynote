package com.caixc.easynoteapp.widget

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.TypedValue
import android.view.View

class StaggeredDividerItemDecoration() : RecyclerView.ItemDecoration() {
    lateinit var mContext: Context
    private var mInterval: Int = 0

    constructor(context: Context, interval: Int) : this() {
        mContext = context
        mInterval = interval
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex = params.spanIndex
        val applyDimension = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,mInterval.toFloat(),mContext.resources.displayMetrics
            )
        if (spanIndex % 2 == 0) {
            outRect.left = 0
        }else{
            outRect.left=mInterval
        }
        outRect.bottom = mInterval
    }
}