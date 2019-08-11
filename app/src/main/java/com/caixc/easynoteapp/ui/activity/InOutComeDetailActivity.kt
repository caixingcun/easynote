package com.caixc.easynoteapp.ui.activity

import android.text.TextUtils
import android.view.View
import android.widget.DatePicker
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.InOutComeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_in_out_come_detail.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.lang.Exception


class InOutComeDetailActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_in_out_come_detail

    companion object {
        const val BEAN = "bean"
    }

    lateinit var bean: InOutComeBean

    override fun initView() {
        iv_left.setOnClickListener {
            finish()
        }

        tv_title.text = "收支录入"

      var ser  = intent.getSerializableExtra(BEAN)
        if (ser != null) {
           bean =  ser as InOutComeBean
        }else{
            bean = InOutComeBean("",0.toDouble(),"",0.toLong())
        }
        tv_right.visibility = if (bean == null) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        if (bean != null) {
            et_money.setText("" + bean.money)
            et_reason.setText("" + bean.reason)
            et_date.setText("" + bean.create_time)
        } else {
            et_money.setText("")
            et_reason.setText("")
            et_date.setText("")
        }

        tv_right.setOnClickListener {
            deleteInOutCome(bean.i_id)
        }

        btn_save.setOnClickListener {
            var reason = et_reason.text.toString().trim()
            var money = et_money.text.toString().trim()
            var date = et_date.text.toString().trim()

            if (TextUtils.isEmpty(reason)) {
                toast("收支原因不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(money)) {
                toast("收支金额不能为空")
                return@setOnClickListener
            }
            try {
                money.toDouble()
            } catch (e: Exception) {
                toast("金额格式有误")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(date)) {
                toast("收支时间不能为空")
                return@setOnClickListener
            }

            var id: Long = 0
            if (bean != null) {
                id = bean.i_id
            }
            postInOutCome(id, money.toDouble(), reason, date)
        }
    }

    private fun deleteInOutCome(id: Long) {
        RetrofitClient().getInstance(Urls.HOST)
            .create(InOutComeService::class.java)
            .deleteInOutCome(id)
            .doOnSubscribe { addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(bean: HttpResult) {
                    toast(bean.msg)
                    finish()
                }
            })
    }


    private fun postInOutCome(id: Long, money: Double, reason: String, time: String) {
        RetrofitClient().getInstance(Urls.HOST)
            .create(InOutComeService::class.java)
            .postInOutCome(id, money, reason, time)
            .doOnSubscribe {
                addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(bean: HttpResult) {
                    toast(bean.msg)
                    finish()
                }
            })
    }
}