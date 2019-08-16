package com.caixc.easynoteapp.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.UserInfo
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.service.LoginService
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.InfoService
import com.caixc.easynoteapp.ui.LoginActivity
import com.caixc.easynoteapp.ui.activity.ChangePwdActivity
import com.caixc.easynoteapp.ui.activity.FeedBackActivity
import com.caixc.easynoteapp.util.ActivityController
import com.luck.picture.lib.PictureSelector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.include_toolbar.*
import android.text.TextUtils
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.caixc.easynoteapp.GlideApp
import com.caixc.easynoteapp.global.Urls.Companion.IMAGE_URL
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tbruyelle.rxpermissions2.RxPermissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class MineFragment : BaseFragment() {

    override fun setLayout(): Int = com.caixc.easynoteapp.R.layout.fragment_mine

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

        cv_header.setOnClickListener {
            RxPermissions(getBaseActivity())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        chooseImage(1)
                    }
                }
        }
    }

    override fun initListener() {
        smart_refresh_layout.setEnableRefresh(true)
        smart_refresh_layout.setRefreshHeader(ClassicsHeader(activity))
        smart_refresh_layout.setOnRefreshListener {
            smart_refresh_layout.finishLoadMore()
            getData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        val selectList = PictureSelector.obtainMultipleResult(data)

        var path = selectList[0].compressPath
        postHeader(path)

    }

    private fun postHeader(path: String) {
        getBaseActivity().showDialog()
        val file = File(path)

        val parts = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
            .build().parts()

        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(InfoService::class.java)
            .postImage(parts)
            .doOnSubscribe { getBaseActivity().addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(getBaseActivity()) {
                override fun onNext(t: HttpResult) {
                    getBaseActivity().hideDialog()
                    getBaseActivity().toast(t.msg)
                    getData()
                }
            })
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

    var userInfo: UserInfo = UserInfo(0, "", "")

    override fun getData() {
        getBaseActivity().showDialog()
        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(InfoService::class.java)
            .getInfo()
            .doOnSubscribe { getBaseActivity().addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<UserInfo>(getBaseActivity()) {
                override fun onNext(t: UserInfo) {
                    getBaseActivity().hideDialog()
                    userInfo = t
                    refreshView()
                }
            })
    }

    private fun refreshView() {

        if (!TextUtils.isEmpty(userInfo.header)) {
            var url = IMAGE_URL + userInfo.header
            var glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder().addHeader("token", Preference.preferences.getString(Constants.TOKEN, "")).build()
            )
            GlideApp.with(this)
                .load(glideUrl)
                .into(cv_header)
        }

        tv_nickname.text = userInfo.nickname
    }
}