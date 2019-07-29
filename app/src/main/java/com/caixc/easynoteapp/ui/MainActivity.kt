package com.caixc.easynoteapp.ui

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.HomeAdapter
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.NoteBean
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.NoteService
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : BaseActivity() {
    private val list = mutableListOf<NoteBean>()

    override fun setLayout(): Int = R.layout.activity_main

    override fun initView() {
        tv_title.text = "主页面"
        iv_right.visibility = View.VISIBLE
        iv_right.setOnClickListener{
            startActivity(Intent(mActivity, NoteDetailActivity::class.java))
        }
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                toast("" + text)
                return true
            }

        })
        initRecyclerView()
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        refresh_layout.setEnableRefresh(true)
        refresh_layout.setRefreshHeader(ClassicsHeader(mActivity))
        refresh_layout.setOnRefreshListener{
            getData()
        }
    }

    lateinit var adapter: HomeAdapter


    private fun initRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(mActivity, 2)
        adapter = HomeAdapter(R.layout.item_home, list)
        recycler_view.adapter = adapter

        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                var intent = Intent(mActivity, NoteDetailActivity::class.java)
                intent.putExtra(NoteDetailActivity.ID, list[position].id)
                startActivity(intent)
            }

        adapter.onItemLongClickListener =
            BaseQuickAdapter.OnItemLongClickListener { _, _, _ ->
                adapter.isSelect = !adapter.isSelect
                adapter.notifyDataSetChanged()
                true
            }

    }

    override fun getData() {
        RetrofitClient().getInstance(Urls.HOST)
            .create(NoteService::class.java)
            .getNotes()
            .doOnSubscribe {
                addDisposable(it)
//                showDialog()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<NoteBean>>(mActivity) {
                override fun onNext(l: List<NoteBean>) {
//                    hideDialog()
                    LogUtils.debug(Gson().toJson(l))
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
