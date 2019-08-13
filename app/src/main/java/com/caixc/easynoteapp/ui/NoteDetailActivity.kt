package com.caixc.easynoteapp.ui

import android.text.TextUtils
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.NoteBean
import com.caixc.easynoteapp.event.NoteEvent
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.NoteService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note_detail.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_note_detail

    companion object {
        const val ID = "id"
    }

    override fun initView() {
        iv_left.setOnClickListener {
            finish()
        }

        tv_title.text = "便签"

        tv_right.setOnClickListener {
            deleteNote(intent.getIntExtra(ID,0))
        }

        btn_save.setOnClickListener {
            var content = et.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                toast("输入内容不能为空")
            } else {
                var time = SimpleDateFormat("yyyy年MM月dd HH:mm").format(Date())
                var id = intent.getIntExtra(ID,0)
                postNote(id, content, time)
            }
        }
    }

    private fun deleteNote(id: Int) {
        RetrofitClient().getInstance(Urls.HOST)
            .create(NoteService::class.java)
            .deleteNote(id)
            .doOnSubscribe { addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(bean: HttpResult) {
                    toast(bean.msg)
                    EventBus.getDefault().post(NoteEvent())
                    finish()
                }
            })
    }


    private fun postNote(id: Int, content: String, time: String) {
        RetrofitClient().getInstance(Urls.HOST)
            .create(NoteService::class.java)
            .postNote(id, content, time)
            .doOnSubscribe {
                addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<HttpResult>(mActivity) {
                override fun onNext(bean: HttpResult) {
                    toast(bean.msg)
                    EventBus.getDefault().post(NoteEvent())
                    finish()
                }
            })
    }

    override fun getData() {
        val id = intent.getIntExtra(ID, 0)
        if (id == 0) {
            tv_right.visibility = View.INVISIBLE
            return
        }
        tv_right.visibility = View.VISIBLE
        RetrofitClient().getInstance(Urls.HOST)
            .create(NoteService::class.java)
            .getNote(id)
            .doOnSubscribe {
                addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<NoteBean>(mActivity) {
                override fun onNext(bean: NoteBean) {
                    et.setText(bean.content)
                }
            })
    }
}