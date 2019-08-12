package com.caixc.easynoteapp.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.ZaiQuanAdapter
import com.caixc.easynoteapp.base.BaseFragment
import com.caixc.easynoteapp.bean.KeyValueBean
import com.caixc.easynoteapp.bean.ZaiQuanBean
import com.caixc.easynoteapp.ui.ZaiQuanSelectActivity
import com.caixc.easynoteapp.ui.activity.TemplatureActivity
import com.caixc.easynoteapp.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_zhaiquan.*
import kotlinx.android.synthetic.main.fragment_zhaiquan.recycler_view
import kotlinx.android.synthetic.main.fragment_zhaiquan.refresh_layout
import kotlinx.android.synthetic.main.include_toolbar.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class TemplatureFragment : BaseFragment() {
    override fun initView() {
        tv_title.text = "长投温度"
        tv_right.visibility = View.VISIBLE
        tv_right.text = "添加"
        tv_right.setOnClickListener {
            startActivity(Intent(getBaseActivity(), TemplatureActivity::class.java))
        }

    }

    override fun setLayout(): Int  = R.layout.fragment_templature


    override fun getData() {

    }

}