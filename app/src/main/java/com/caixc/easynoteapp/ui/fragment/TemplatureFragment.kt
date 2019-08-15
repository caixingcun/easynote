package com.caixc.easynoteapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.TemplatureAdapter
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.bean.TemplatureBean
import com.caixc.easynoteapp.event.TemplatureEvent
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.TemperatureService
import com.caixc.easynoteapp.ui.activity.TemplatureActivity
import com.caixc.easynoteapp.ui.activity.TemplatureImmediateActivity
import com.caixc.easynoteapp.ui.activity.TemplatureQuickActivity
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_templature.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.item_templature_header.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class TemplatureFragment : BaseFragment() {

   var date =  SimpleDateFormat("yyyyMMdd").format(Date())


    override fun initView() {
        tv_title.text = "长投温度"
        tv_right.visibility = View.VISIBLE
        tv_right.text = "添加"
        tv_right.setOnClickListener {
          //  startActivity(Intent(getBaseActivity(), TemplatureActivity::class.java))
//            startActivity(Intent(getBaseActivity(), TemplatureQuickActivity::class.java))
            startActivity(Intent(getBaseActivity(), TemplatureImmediateActivity::class.java))
        }
        initRecyclerView()
        initRefreshLayout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun templatureListener(event: TemplatureEvent) {
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    private fun initRefreshLayout() {
        smart_refresh_layout.setEnableRefresh(true)
        smart_refresh_layout.setRefreshHeader(ClassicsHeader(context))
        smart_refresh_layout.setOnRefreshListener {
            getData()
        }
    }

    var list = mutableListOf<TemplatureBean>()
    lateinit var adapter: TemplatureAdapter
    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = TemplatureAdapter(R.layout.item_templature, list)
        recycler_view.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            list[position]
            var intent = Intent(activity, TemplatureActivity::class.java)
            intent.putExtra(TemplatureActivity.BEAN, list[position])
            startActivity(intent)
        }
        var headerView = View.inflate(context, R.layout.item_templature_header, null)
        headerView.tv_date.setText(date)

        adapter.addHeaderView(headerView)

        adapter.setEmptyView(R.layout.item_empty,recycler_view)
    }

    override fun setLayout(): Int = R.layout.fragment_templature


    override fun getData() {
        getBaseActivity().showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(TemperatureService::class.java)
            .getTemplate(date)
            .doOnSubscribe { getBaseActivity().addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<TemplatureBean>>(getBaseActivity()) {
                override fun onNext(t: List<TemplatureBean>) {
                    getBaseActivity().hideDialog()
                    smart_refresh_layout.finishRefresh(true)
                    list.clear()
                    list.addAll(t)
                    val selector: (TemplatureBean) -> Double? = {
                        it.templature
                    }
                    list.sortBy(selector)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    smart_refresh_layout.finishRefresh(false)
                }
            })

    }

}