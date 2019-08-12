package com.caixc.easynoteapp.ui

import android.content.Intent
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.Preference
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * 闪屏页
 */
class SplashActivity : BaseActivity() {
    override fun getData() {

        val token = Preference().getValue("token", "")
        Observable.interval(1, TimeUnit.MILLISECONDS)
            .take(1)
            .subscribe {
                if (token!!.isNotEmpty()) {
                    startActivity(Intent(mActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(mActivity, LoginActivity::class.java))
                }
                    finish()
            }

    }

    override fun refreshView() {

    }

    override fun setLayout(): Int = R.layout.activity_splash


}