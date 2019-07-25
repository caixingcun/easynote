package com.caixc.easynoteapp.ui

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.HomeAdapter
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.InOutComeBean
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val list = mutableListOf<InOutComeBean>()

    override fun setLayout(): Int = R.layout.activity_main

    override fun initView() {
        tv_title.text = "便签"
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {

                toast("" + p0)
                return true
            }

        })
        initRecyclerView()
    }

    private fun initRecyclerView() {

        recycler_view.layoutManager = GridLayoutManager(mActivity, 2)

        var adapter = HomeAdapter(R.layout.item_home, list)

        recycler_view.adapter = adapter

        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                startActivity(
                    Intent(
                        mActivity,
                        InOutComeDetailActivity::class.java
                    )
                )
            }

        adapter.onItemLongClickListener =
            BaseQuickAdapter.OnItemLongClickListener { _, _, _ ->
                adapter.isSelect = !adapter.isSelect
                adapter.notifyDataSetChanged()
                true
            }

    }

    override fun getData() {


    }

}
