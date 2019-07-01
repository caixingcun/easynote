package com.caixc.easynoteapp.ui

import android.os.Bundle
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.retrofit.RetrofitHelper
import com.caixc.easynoteapp.retrofit.RetrofitService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.experimental.async

class LoginActivity : BaseActivity() {


    override fun initListener() {
        btn_login.setOnClickListener {


        }


    }

    private val retrofit = RetrofitHelper.create("").create(RetrofitService::class.java)

    override fun getData() {
    retrofit
        .login<LoginBean>("{\"userName\":\"caixc\",\"password\":\"123456\"}")



    }

    override fun refeshView() {

    }

    override fun setLayout(): Int = R.layout.activity_login
}