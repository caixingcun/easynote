package com.caixc.easynoteapp.ui.activity

import android.text.TextUtils
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.FeedBackService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_feed_back.*
import kotlinx.android.synthetic.main.include_toolbar.*

class FeedBackActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_feed_back

    override fun initView() {
        tv_title.text = "反馈"

        iv_left.setOnClickListener {
            finish()
        }

        btn_save.setOnClickListener {
            val content = et_content.text.toString().trim()
            val phone = et_contact.text.toString().trim()
            if (!emptyCheck(phone, content)) {
                return@setOnClickListener
            }
            submit(phone, content)
        }

    }

    private fun submit(phone: String, content: String) {
        showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(FeedBackService::class.java)
            .submitFeedBack(phone, content)
            .doOnSubscribe { addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(bean: HttpResult) {
                    hideDialog()
                    toast("提交成功")
                    finish()
                }
            })


    }

    private fun emptyCheck(phone: String, content: String): Boolean {
        if (TextUtils.isEmpty(phone)) {
            toast("请输入联系方式")
            return false
        }
        if (TextUtils.isEmpty(content)) {
            toast("请输入需要反馈内容")
            return false
        }
        return true
    }
}