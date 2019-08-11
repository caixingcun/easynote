package com.caixc.easynoteapp.ui.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.InOutComeAdapter
import com.caixc.easynoteapp.adapter.NoteAdapter
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.bean.NoteBean
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.InOutComeService
import com.caixc.easynoteapp.net.service.NoteService
import com.caixc.easynoteapp.ui.NoteDetailActivity
import com.caixc.easynoteapp.ui.activity.InOutComeDetailActivity
import com.caixc.easynoteapp.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_note_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

class InOutComeFragment:BaseFragment(){
    override fun setLayout(): Int  = R.layout.fragment_in_out_come

    private var list: MutableList<InOutComeBean> = mutableListOf()
    private var listAll: MutableList<InOutComeBean> = mutableListOf()
    lateinit var adapter: InOutComeAdapter

    override fun initView() {
        tv_title.text = "收支记录"
        iv_left.visibility = View.INVISIBLE
        iv_right.visibility = View.VISIBLE
        iv_right.setOnClickListener{
            var intent = Intent(context, InOutComeDetailActivity::class.java)
            startActivity(intent)
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                var listTemplate = mutableListOf<InOutComeBean>()
                if (!TextUtils.isEmpty(text.toString().trim())) {
                    var l  =   listAll.filter {
                        text.toString() in it.reason||text.toString() in it.create_time
                    }
                    listTemplate.addAll(l)
                }else{
                    listTemplate.addAll(listAll)
                }
                list.clear()
                list.addAll(listTemplate)
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }
        })

        initRecyclerView()
        initRefreshLayout()
    }
    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context )
        adapter = InOutComeAdapter(R.layout.item_in_out_come, list)
        recycler_view.adapter = adapter

        adapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    var intent = Intent(context, InOutComeDetailActivity::class.java)
                    intent.putExtra(InOutComeDetailActivity.BEAN, list[position])
                    startActivity(intent)
                }

        adapter.onItemLongClickListener =
                BaseQuickAdapter.OnItemLongClickListener { _, _, _ ->
                    adapter.isSelect = !adapter.isSelect
                    adapter.notifyDataSetChanged()
                    true
                }

        adapter.setEmptyView(R.layout.item_empty,recycler_view)
    }

    private fun initRefreshLayout() {
        refresh_layout.setEnableRefresh(true)
        refresh_layout.setRefreshHeader(ClassicsHeader(context))
        refresh_layout.setOnRefreshListener{
            getData()
        }
    }



    override fun getData() {
        getBaseActivity().showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(InOutComeService::class.java)
            .getInOutComes()
            .doOnSubscribe {
                getBaseActivity().addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<InOutComeBean>>(getBaseActivity()) {
                override fun onNext(l: List<InOutComeBean>) {
                    getBaseActivity().hideDialog()
                    LogUtils.debug(Gson().toJson(l))
                    listAll .clear()
                    listAll.addAll(l)

                    list.clear()
                    list.addAll(l)
                    adapter.notifyDataSetChanged()
                    refresh_layout.finishRefresh(true)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    refresh_layout.finishRefresh(false)
                }
            })
    }

}