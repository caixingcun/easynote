package com.caixc.easynoteapp.ui

import android.util.Log
import android.widget.Toast
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.retrofit.RetrofitHelper
import com.caixc.easynoteapp.retrofit.RetrofitService
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.HttpException

class LoginActivity : BaseActivity() {


    override fun initListener() {
        btn_login.setOnClickListener {

            var loginBean = LoginBean(et_account.text.toString().trim(),et_pwd.text.toString().trim())
            val body =
                RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(loginBean))
            RetrofitHelper.create("https://139.196.87.25").create(RetrofitService::class.java)
                .login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginResultBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: LoginResultBean) {
                        Log.d("tag", Gson().toJson(t))
                        Toast.makeText(this@LoginActivity, Gson().toJson(t), Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            e.code()
                            Logutil.debug("code" + e.code())
                        }

                    }

                })

        }


    }


    override fun getData() {


    }

    override fun refeshView() {

    }

    override fun setLayout(): Int = R.layout.activity_login
}