package com.caixc.easynoteapp.ui

import android.speech.tts.TextToSpeech
import android.util.Log
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.retrofit.RetrofitClient
import com.caixc.easynoteapp.retrofit.RetrofitException
import com.caixc.easynoteapp.retrofit.RetrofitService
import com.caixc.easynoteapp.retrofit.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {


    override fun initListener() {
        btn_login.setOnClickListener {
            var loginBean = LoginBean(et_account.text.toString().trim(), et_pwd.text.toString().trim())

            RetrofitClient().getInstance("https://139.196.87.25")
                .create(RetrofitService::class.java)
                .login(RetrofitUtils.convertJson2RequestBody(Gson().toJson(loginBean)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginResultBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: LoginResultBean) {
                        Log.d("tag", Gson().toJson(t))
                        toast(Gson().toJson(t))
                    }

                    override fun onError(e: Throwable) {
                        val retrofitException = RetrofitException.retrofitException(e)
                        var code = retrofitException.code
                        var message = retrofitException.message

                        toast("code${code}message$message")
                    }

                })
        }
        btn_speech.setOnClickListener {




        }

    }

    lateinit var tts: TextToSpeech



    override fun getData() {


    }

    override fun refeshView() {

    }

    override fun setLayout(): Int = com.caixc.easynoteapp.R.layout.activity_login
}