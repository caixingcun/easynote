package com.caixc.easynoteapp.ui

import android.util.Log
import android.widget.Toast
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.GankBean
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

class LoginActivity : BaseActivity() {


    override fun initListener() {
        btn_login.setOnClickListener {
            RetrofitHelper.create("http://gank.io").create(RetrofitService::class.java)
                .gank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :Observer<GankBean>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: GankBean) {
                        Log.d("tag", Gson().toJson(t))
                Toast.makeText(this@LoginActivity,Gson().toJson(t),Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        Log.d("tag",e.toString())
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