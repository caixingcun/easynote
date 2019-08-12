package com.caixc.easynoteapp.ui.activity

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.TemplatureService
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_templature.*
import java.lang.Exception

class TemplatureActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_templature

    lateinit var mList: List<String>
    var type: String = ""

    override fun initView() {

    }
    override fun initListener() {
        tag_flow_layout.setOnSelectListener {
            type = mList[it.toList()[0]]
        }
        btn_submit.setOnClickListener {
            var time = et_date.text.toString().trim()
            var temperature = et_temperature.text.toString().trim()
            var code_in = et_code_in.text.toString().trim()
            var code_out = et_code_out.text.toString().trim()

            if (!emptyCheck(time,type,temperature,code_in,code_out)) {
                return@setOnClickListener
            }

            commit(time,type,temperature.toDouble(),code_in,code_out)
        }
    }

    private fun commit(time: String, index_type: String, template: Double, code_in: String, code_out: String) {
        showDialog()
        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(TemplatureService::class.java)
            .postTemplature(time, index_type, template, code_in, code_out)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(r: HttpResult) {
                    hideDialog()
                    toast(r.msg)
                    finish()
                }
            })
    }

    private fun emptyCheck(
        time: String,
        type: String,
        temperature: String,
        code_in: String,
        code_out: String
    ): Boolean {
        if (TextUtils.isEmpty(type)) {
            toast("请选择,指数类型")
            return false
        }
        if (TextUtils.isEmpty(temperature)) {
            toast("请输入指数温度")
            return false
        }
        try {
            temperature.toDouble()
        } catch (e: Exception) {
            toast("温度格式不正确")
            return false
        }
        if (TextUtils.isEmpty(time)) {
            toast("请输入长投温度日期")
            return false
        }
        if (TextUtils.isEmpty(code_in)) {
            toast("请输入场内代码")
            return false
        }
        if (TextUtils.isEmpty(code_out)) {
            toast("请输入场外代码")
            return false
        }

        return true
    }

    override fun getData() {
        showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(TemplatureService::class.java)
            .getIndexs()
            .doOnSubscribe {
                addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<String>>(mActivity) {
                override fun onNext(l: List<String>) {
                    hideDialog()
                    tag_flow_layout.setMaxSelectCount(1)
                     mList   = l
                    tag_flow_layout.adapter = object : TagAdapter<String>(l) {
                        override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                            val view = View.inflate(mActivity, R.layout.item_flow_layout, null)
                            var tv = view.findViewById<TextView>(R.id.tv)
                            tv.text = t
                            return tv
                        }
                    }

                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                }
            })
    }
}