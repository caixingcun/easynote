package com.caixc.easynoteapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.Preference

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }




}