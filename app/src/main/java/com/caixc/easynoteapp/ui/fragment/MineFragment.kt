package com.caixc.easynoteapp.ui.fragment

import android.content.Intent
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.service.LoginService
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.ui.LoginActivity
import com.caixc.easynoteapp.ui.activity.ChangePwdActivity
import com.caixc.easynoteapp.ui.activity.FeedBackActivity
import com.caixc.easynoteapp.util.ActivityController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MineFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.fragment_mine

    override fun initView() {
        tv_title.text = "我的"
        iv_left.visibility = View.INVISIBLE

        btn_logout.setOnClickListener {
            logout()
        }
        tv_change_pwd.setOnClickListener {
            startActivity(Intent(context, ChangePwdActivity::class.java))
        }
        tv_feed_back.setOnClickListener {
            startActivity(Intent(context, FeedBackActivity::class.java))
        }
    }





    private fun logout() {
        getBaseActivity().showDialog()
        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(LoginService::class.java)
            .logout()
            .doOnSubscribe { getBaseActivity().addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(getBaseActivity()) {
                override fun onNext(t: HttpResult) {
                    getBaseActivity().hideDialog()
                    Preference().setValue(Constants.TOKEN, "")
                    ActivityController.removeAll()
                    startActivity(Intent(mActivity, LoginActivity::class.java))
                }
            })

    }
}