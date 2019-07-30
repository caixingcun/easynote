package com.caixc.easynoteapp.ui

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class InOutComeActivity : BaseActivity() {
    override fun setLayout(): Int  = R.layout.activity_in_out_come

    override fun initView() {
        tv_title.text = "收支记录"
        iv_left.setOnClickListener{
            finish()
        }
    }

}