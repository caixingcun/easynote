package com.caixc.easynoteapp.ui

import android.content.Intent
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.LoginService
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.util.ActivityController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_mine.*
import kotlinx.android.synthetic.main.include_toolbar.*


class MineActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_mine
    override fun initView() {
        tv_title.text = "我的"
        iv_left.setOnClickListener{
            finish()
        }
        btn_logout.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        showDialog()
        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(LoginService::class.java)
            .logout()
            .doOnSubscribe { addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(t: HttpResult) {
                    hideDialog()
                    Preference().setValue(Constants.TOKEN, "")
                    ActivityController.removeAll()
                    startActivity(Intent(mActivity, LoginActivity::class.java))
                    toast(t.msg)
                }
            })

    }
}