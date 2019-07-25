package com.caixc.easynoteapp.ui

import android.content.Intent
import android.util.Log
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.RetrofitException
import com.caixc.easynoteapp.net.RetrofitService
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    override fun initListener() {
        btn_login.setOnClickListener {
            var account: String = et_account.text.toString().trim()
            val password = et_pwd.text.toString().trim()

            RetrofitClient().getInstance(Urls.HOST)
                .create(RetrofitService::class.java)
                .login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MyDefaultObserver<LoginResultBean>(mActivity) {
                    override fun onNext(t: LoginResultBean) {
                        Preference().setValue(Constants.TOKEN,t.token)
                        startActivity(Intent(mActivity,MainActivity::class.java))
                    }
                })

        }


    }


    override fun setLayout(): Int = com.caixc.easynoteapp.R.layout.activity_login
}