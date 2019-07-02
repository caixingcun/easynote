package com.caixc.easynoteapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.base.Preference

class SplashActivity : BaseActivity() {
    override fun getData() {

        val token = Preference().getValue("token", "")

//        if (token?.isNotEmpty()) {
//            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//        }else{
//            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//        }
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    override fun refeshView() {

    }

    override fun setLayout(): Int = R.layout.activity_splash


}