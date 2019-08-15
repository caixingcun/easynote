package com.caixc.easynoteapp.ui.activity

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.event.TemplatureEvent
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.TemperatureService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_templature_imm.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

class TemplatureImmediateActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_templature_imm

    override fun initView() {
        var date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var data =
            "[{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"中证500\",\"code_in\":\"510500\",\"code_out\":\"160119\",\"templature\":5.3},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"红利指数\",\"code_in\":\"510880\",\"code_out\":\"---\",\"templature\":10.6},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"中证红利\",\"code_in\":\"---\",\"code_out\":\"100032\",\"templature\":13.3},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"恒生指数\",\"code_in\":\"159920\",\"code_out\":\"164705\",\"templature\":17.3},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"国企指数\",\"code_in\":\"510900\",\"code_out\":\"110031\",\"templature\":26},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"上证50\",\"code_in\":\"510050\",\"code_out\":\"110003\",\"templature\":26.6},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"上证180\",\"code_in\":\"510180\",\"code_out\":\"040180\",\"templature\":31},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"沪深300\",\"code_in\":\"510300\",\"code_out\":\"160706\",\"templature\":33.7},\n" +
                    "{\"a_id\":0,\"create_time\":\"" + date + "\",\"index_type\":\"创业板指\",\"code_in\":\"159915\",\"code_out\":\"161022\",\"templature\":45.9}]"

        et_content.setText(formatJson(data))
    }

    override fun initListener() {
        btn_save.setOnClickListener {
            showDialog()
            var requestbody =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8"), et_content.text.toString())

            RetrofitClient().getInstance(Urls.HOST)
                .create(TemperatureService::class.java)
                .postTemplatures(requestbody)
                .doOnSubscribe {
                    addDisposable(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                    override fun onNext(t: HttpResult) {
                        hideDialog()
                        EventBus.getDefault().post(TemplatureEvent())
                        finish()
                    }
                })
        }
    }

    override fun getData() {

    }

}
