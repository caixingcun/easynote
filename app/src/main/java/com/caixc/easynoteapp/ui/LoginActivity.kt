package com.caixc.easynoteapp.ui

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.LoginService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_pwd.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn
import kotlinx.android.synthetic.main.activity_login.et_account
import kotlinx.android.synthetic.main.activity_login.et_pwd
import kotlinx.android.synthetic.main.activity_login.tv
import kotlinx.android.synthetic.main.activity_login.tv_no_auth
import java.util.concurrent.TimeUnit


class LoginActivity : BaseActivity() {
    var isLogin: Boolean = true

    override fun initListener() {
        tv_change.setOnClickListener {
            isLogin = !isLogin
            refreshView()
            Observable.interval(300,300, TimeUnit.MILLISECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
        }
        tv_no_auth.setOnClickListener {
            startActivity(Intent(mActivity,MainActivity::class.java))
            finish()
        }

        btn.setOnClickListener {
            var account: String = et_account.text.toString().trim()
            val password = et_pwd.text.toString().trim()

            if (!emptyCheck(account, password)) {
                return@setOnClickListener
            }
            if (isLogin) {
                showDialog()
                RetrofitClient().getInstance(Urls.HOST)
                    .create(LoginService::class.java)
                    .login(account, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : MyDefaultObserver<LoginResultBean>(mActivity) {
                        override fun onNext(t: LoginResultBean) {
                            hideDialog()
                            Preference().setValue(Constants.TOKEN, t.token)
                            startActivity(Intent(mActivity, MainActivity::class.java))
                            finish()
                        }
                    })

            } else {
                showDialog()
                RetrofitClient().getInstance(Urls.HOST)
                    .create(LoginService::class.java)
                    .register(account, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : MyDefaultObserver<LoginResultBean>(mActivity) {
                        override fun onNext(t: LoginResultBean) {
                            hideDialog()
                            Preference().setValue(Constants.TOKEN, t.token)
                            startActivity(Intent(mActivity, MainActivity::class.java))
                            finish()
                        }
                    })

            }


        }


    }

    private fun emptyCheck(account: String, password: String): Boolean {
        if (TextUtils.isEmpty(account)) {
            toast("账号不能为空")
            return false
        }
        if (TextUtils.isEmpty(password)) {
            toast("密码不能为空")
            return false
        }
        return true
    }

    override fun refreshView() {
        if (isLogin) {
            tv.text = "登录"
            tv_change.text = "还没有账号，去注册！"
            btn.text = "立即登录"
        } else {
            tv.text = "注册"
            tv_change.text = "有账号，去登录！"
            btn.text = "立即注册"
        }

    }

    override fun initView() {

        tv_no_auth.visibility = View.INVISIBLE
    }

    override fun setLayout(): Int = com.caixc.easynoteapp.R.layout.activity_login
}