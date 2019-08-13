package com.caixc.easynoteapp.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.caixc.easynoteapp.adapter.TemplatureEditAdapter
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.TemplatureBean
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.TemperatureService
import com.chad.library.adapter.base.listener.OnItemDragListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_templature_quick.*
import java.text.SimpleDateFormat
import java.util.*
import android.support.v7.widget.helper.ItemTouchHelper
import com.caixc.easynoteapp.R
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.item_templature_header.*


class TemplatureQuickActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_templature_quick

    var date = SimpleDateFormat("yyyy-MM-dd").format(Date())
    override fun initView() {
        tv_date.text = date
    }

    var list = mutableListOf<TemplatureBean>()
    lateinit var adapter: TemplatureEditAdapter

    override fun getData() {
        showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(TemperatureService::class.java)
            .getIndexs()
            .doOnSubscribe {
                addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<String>>(mActivity) {
                override fun onNext(l: List<String>) {
                    hideDialog()
                    val listTemp = l.map {
                        TemplatureBean(0, "", "", date, it, 0.toDouble())
                    }
                    list.clear()
                    list.addAll(listTemp)
                    adapter = TemplatureEditAdapter(R.layout.item_templature_edit, list)
                    recycler_view.layoutManager = LinearLayoutManager(mActivity)
                    recycler_view.adapter = adapter

                    val itemDragAndSwipeCallback = ItemDragAndSwipeCallback(adapter)
                    val itemTouchHelper = ItemTouchHelper(itemDragAndSwipeCallback)
                    itemTouchHelper.attachToRecyclerView(recycler_view)

                    adapter.enableDragItem(itemTouchHelper, R.id.card_view,true)
                    adapter.setOnItemDragListener(object:OnItemDragListener{
                        override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

                        }

                        override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

                        }

                        override fun onItemDragMoving(source: RecyclerView.ViewHolder?, from: Int, target: RecyclerView.ViewHolder?, to: Int) {

                        }
                    })


                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                }
            })
    }
}