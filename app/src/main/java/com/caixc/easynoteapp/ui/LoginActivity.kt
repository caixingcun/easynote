package com.caixc.easynoteapp.ui

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.retrofit.RetrofitHelper
import com.caixc.easynoteapp.retrofit.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {


    override fun initListener() {
        btn_login.setOnClickListener {


        }


    }

    private val retrofit = RetrofitHelper.create("https://139.196.87.25").create(RetrofitService::class.java)

    override fun getData() {
        retrofit
            .login("{\"userName\":\"18606291073\",\"password\":\"123456\"}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<LoginResultBean>{
                override fun onComplete() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onSubscribe(d: Disposable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: LoginResultBean) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })



    }

    override fun refeshView() {

    }

    override fun setLayout(): Int = R.layout.activity_login
}