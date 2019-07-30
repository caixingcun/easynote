package com.caixc.easynoteapp.ui

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.NoteAdapter
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
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

class NoteListActivity:BaseActivity(){

    private var list: MutableList<NoteBean> = ArrayList()

    override fun setLayout(): Int = R.layout.activity_note_list

    override fun initView() {
        tv_title.text = "日记本"
        iv_right.visibility = View.VISIBLE
        iv_left.setOnClickListener{
            finish()
        }
        iv_right.setOnClickListener{
            startActivity(Intent(mActivity, NoteDetailActivity::class.java))
        }
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                   var listTemplate =   list.filter {
                        text.toString() in it.content
                    }

                list.clear()
                list.addAll(listTemplate)
                adapter.notifyDataSetChanged()
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

    lateinit var adapter: NoteAdapter


    private fun initRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(mActivity, 2)
        adapter = NoteAdapter(R.layout.item_home, list)
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

        adapter.setEmptyView(R.layout.item_empty,recycler_view)
    }

    override fun getData() {
        showDialog()
        RetrofitClient().getInstance(Urls.HOST)
            .create(NoteService::class.java)
            .getNotes()
            .doOnSubscribe {
                addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<NoteBean>>(mActivity) {
                override fun onNext(l: List<NoteBean>) {
                    hideDialog()
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