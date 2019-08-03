package com.caixc.easynoteapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.adapter.ZaiQuanAdapter
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.bean.KeyValueBean
import com.caixc.easynoteapp.bean.ZaiQuanBean
import com.caixc.easynoteapp.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_note_list.refresh_layout
import kotlinx.android.synthetic.main.activity_zhaiquan.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import kotlin.concurrent.thread


class ZaiQuanActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_zhaiquan
    var list = mutableListOf<ZaiQuanBean>()
    private lateinit var adapter: ZaiQuanAdapter
    override fun initView() {
        initRecyclerView()
        initRefreshLayout()
        initWebView()

    }


    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        val jsQuery = "javascript:" + getJsQueryStr(this, "getyisilu.js")
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl("https://www.jisilu.cn/data/cbnew/#cb")
        web_view.webChromeClient = object : WebChromeClient() {
        }
        web_view.addJavascriptInterface(object : JsCallback {
            @JavascriptInterface
            override fun onCallback(content: String) {
                runOnUiThread {
                    LogUtils.debug("$content---")
                    val listTemplate = mutableListOf<ZaiQuanBean>()
                    val jsonArray = JSONArray(content)
                    for (i in 0..jsonArray.length()) {
                        var zaiQuanBean = ZaiQuanBean()
                        val arrayChild = JSONArray(jsonArray[i].toString())
                        for (j in 0..arrayChild.length()) {
                            val json =
                                Gson().fromJson<KeyValueBean>(arrayChild[i].toString(), KeyValueBean::class.java)
                            if (!TextUtils.isEmpty(json.value)) {
                                when (json.name) {
                                    "bond_id" -> zaiQuanBean.bond_id = json.value
                                    "bond_nm" -> zaiQuanBean.bond_nm = json.value
                                    "price" -> zaiQuanBean.price = json.value as Double
                                    "stock_nm" -> zaiQuanBean.stock_nm = json.value
                                    "premium_rt" -> zaiQuanBean.premium_rt = json.value
                                    "rating_cd" -> zaiQuanBean.rating_cd = json.value
                                    "year_left" -> zaiQuanBean.year_left = json.value
                                    "ytm_rt_tax" -> zaiQuanBean.ytm_rt_tax = json.value
                                }
                            }
                        }
                        listTemplate.add(zaiQuanBean)
                    }
                    list.clear()
                    list.addAll(listTemplate)
                    adapter.notifyDataSetChanged()
//                    JSONArray(JSONArray(content)[0].toString())[0]
                }
            }

        }, "Android")
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.evaluateJavascript(jsQuery) { s -> LogUtils.debug("$s evaluateJavascript") }
            }
        }
    }

    private fun initRefreshLayout() {
        refresh_layout.setEnableRefresh(true)
        refresh_layout.setRefreshHeader(ClassicsHeader(mActivity))
        refresh_layout.setOnRefreshListener {
            getData()
        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(mActivity, 2)

        adapter = ZaiQuanAdapter(R.layout.item_zaiquan, list)
        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->

            }

        recycler_view.adapter = adapter

    }

    override fun getData() {
        thread {
            var l = mutableListOf<ZaiQuanBean>()
            val document: Document
            try {
                document =
                    Jsoup.connect("https://www.jisilu.cn/data/cbnew/#cb").timeout(30000).userAgent("UA")
                        .validateTLSCertificates(false)
                        .get()

                LogUtils.debug(document.toString())


//                for (tr in tbody.getElementsByTag("tr")) {
//                    var bean = ZaiQuanBean()
//                    for (td in tr.getElementsByTag("td")) {
//                        val dataName = td.attr("data-name")
//                        val value = td.text()
//                        when (dataName) {
//                            "bond_id" -> bean.bond_id = value
//                            "bond_nm" -> bean.bond_nm = value
//                            "price" -> bean.price = value as Double
//                            "stock_nm" -> bean.stock_nm = value
//                            "premium_rt" -> bean.premium_rt = value
//                            "rating_cd" -> bean.rating_cd = value
//                            "year_left" -> bean.year_left = value
//                            "ytm_rt_tax" -> bean.ytm_rt_tax = value
//                        }
//                    }
//                    l.add(bean)
//                    LogUtils.debug("l.size" + l.size)
//
//                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            runOnUiThread {
                list.clear()
                list.addAll(l)
                adapter.notifyDataSetChanged()
                refresh_layout.finishRefresh()
            }
        }
    }

    fun getJsQueryStr(context: Context, assetName: String): String {
        var `in`: InputStream? = null
        var fromFile: ByteArrayOutputStream? = null
        try {
            `in` = context.assets.open(assetName)
            val buff = ByteArray(1024)
            fromFile = ByteArrayOutputStream()
            do {
                val numread = `in`!!.read(buff)
                if (numread <= 0) {
                    break
                }
                fromFile.write(buff, 0, numread)
            } while (true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return fromFile!!.toString()
    }

    interface JsCallback {
        fun onCallback(content: String)
    }
}
