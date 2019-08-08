package com.caixc.easynoteapp.ui.fragment

import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseFragment
import kotlinx.android.synthetic.main.include_toolbar.*

class InOutComeFragment:BaseFragment(){
    override fun setLayout(): Int  = R.layout.fragment_in_out_come


    override fun initView() {
        tv_title.text = "收支记录"
        iv_left.visibility = View.INVISIBLE
    }


}