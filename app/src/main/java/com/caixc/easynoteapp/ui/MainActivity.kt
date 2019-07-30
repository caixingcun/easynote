package com.caixc.easynoteapp.ui

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.HomeModuleAdapter
import com.caixc.easynoteapp.base.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_main

    private var adapter: HomeModuleAdapter? = null
    private var list: MutableList<Pair<String, Int>>? = null


    override fun initView() {
        iv_left.visibility = View.INVISIBLE
        tv_title.text  = "小工具"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(mActivity, 2)

        list = mutableListOf()
        list!!.add(Pair("日记", R.drawable.ic_note))
        list!!.add(Pair("记账", R.drawable.ic_in_out_come))
        list!!.add(Pair("我的", R.drawable.ic_mine))
        adapter = HomeModuleAdapter(R.layout.item_main_module, list)
        adapter!!.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                when (list!![position].first) {
                    "日记" -> startActivity(Intent(mActivity, NoteListActivity::class.java))
                    "记账" -> startActivity(Intent(mActivity, InOutComeActivity::class.java))
                    "我的" -> startActivity(Intent(mActivity, MineActivity::class.java))
                    else -> {
                    }
                }

            }

        recycler_view.adapter = adapter

    }

}
