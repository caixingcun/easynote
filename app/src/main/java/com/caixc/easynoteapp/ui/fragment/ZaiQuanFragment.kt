package com.caixc.easynoteapp.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
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

class ZaiQuanFragment : BaseFragment() {
    override fun setLayout(): Int  = R.layout.fragment_zhaiquan

    companion object {
        const val REQUEST_CODE = 10001
    }

    var list = mutableListOf<ZaiQuanBean>()
    val listTemplate = mutableListOf<ZaiQuanBean>()
    private lateinit var adapter: ZaiQuanAdapter
    override fun initView() {
        tv_title.text = "可转债"
        tv_right.text = "筛选"
        tv_right.visibility = View.VISIBLE
        iv_left.visibility = View.INVISIBLE
        initRecyclerView()
        initRefreshLayout()
        initWebView()

    }

    override fun initListener() {
        tv_right.setOnClickListener {
            var intent = Intent(context, ZaiQuanSelectActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private val jsquery: String
        get() {
            val jsQuery = "javascript:" + getJsQueryStr(context!!, "getyisilu.js")
            return jsQuery
        }

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        val jsQuery = jsquery
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl("https://www.jisilu.cn/data/cbnew/#cb")
        web_view.webChromeClient = object : WebChromeClient() {
        }
        web_view.addJavascriptInterface(object : JsCallback {
            @JavascriptInterface
            override fun onCallback(content: String) {
                getBaseActivity().runOnUiThread {
                    getBaseActivity().hideDialog()
                    LogUtils.debug("$content---")
                    listTemplate.clear()
                    val jsonArray = JSONArray(content)
                    for (i in 0 until jsonArray.length()) {
                        var zaiQuanBean = ZaiQuanBean()
                        val arrayChild = JSONArray(jsonArray[i].toString())
                        for (j in 0 until arrayChild.length()) {
                            val json =
                                Gson().fromJson<KeyValueBean>(arrayChild[j].toString(), KeyValueBean::class.java)
                            if (!TextUtils.isEmpty(json.value)) {
                                when (json.name) {
                                    "bond_id" -> zaiQuanBean.bond_id = json.value
                                    "bond_nm" -> zaiQuanBean.bond_nm = json.value
                                    "price" -> zaiQuanBean.price = json.value.toDouble()
                                    "stock_nm" -> zaiQuanBean.stock_nm = json.value
                                    "premium_rt" -> zaiQuanBean.premium_rt = json.value
                                    "rating_cd" -> zaiQuanBean.rating_cd = json.value
                                    "year_left" -> zaiQuanBean.year_left = json.value
                                    "ytm_rt_tax" -> zaiQuanBean.ytm_rt_tax = json.value
                                }
                            }
                        }
                        if (!zaiQuanBean.bond_nm!!.contains("EB")) {
                            listTemplate.add(zaiQuanBean)
                        }
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
                getBaseActivity().showDialog()
                view?.evaluateJavascript(jsQuery) { s -> LogUtils.debug("$s evaluateJavascript") }
            }
        }
    }

    private fun initRefreshLayout() {
        refresh_layout.setEnableRefresh(true)
        refresh_layout.setRefreshHeader(ClassicsHeader(context))
        refresh_layout.setOnRefreshListener {
            it.finishRefresh()
            //        web_view.evaluateJavascript(jsquery,null)
        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context)

        adapter = ZaiQuanAdapter(R.layout.item_zaiquan, list)
        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                showList(position)
            }

        recycler_view.adapter = adapter

    }

    private fun showList(position: Int) {

        val items = arrayOf("雪球网看历史", "集思录看详情")
        var builder = AlertDialog.Builder(context!!)
            .setTitle("")
            .setItems(items) { dialog, which ->
                dialog.dismiss()
                var intent = Intent()
                intent.action = "android.intent.action.VIEW"
                var uri: Uri
                when (which) {
                    0 -> {
                        uri = Uri.parse("https://xueqiu.com/k?q=" + list[position].bond_id)
                    }
                    else -> {
                        uri = Uri.parse("https://www.jisilu.cn/data/convert_bond_detail/" + list[position].bond_id)
                    }
                }
                intent.data = uri
                startActivity(intent)
            }
        builder.create().show()
    }

    override fun getData() {

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE) {
            val quality = data?.getStringExtra("quality")
            val price = data?.getStringExtra("price")
            val premium = data?.getStringExtra("premium")
            val year = data?.getStringExtra("year")
            val annul = data?.getStringExtra("annul")
            LogUtils.debug("quality" + quality + "price" + price + "premium" + premium + "year" + year + "annul" + annul)

            syncSelectData(quality, price, premium, year, annul)

        }
    }

    private fun syncSelectData(
        quality: String?,
        price: String?,
        premium: String?,
        year: String?,
        annul: String?
    ) {
        list.clear()
        for (zaiQuanBean in listTemplate) {
            val quality_compare = compareQuality(zaiQuanBean, quality)
            val price_compare = comparePrice(zaiQuanBean, price)
            val premium_compare = comparePremium(zaiQuanBean, premium)
            val quality_year = compareYear(zaiQuanBean, year)
            val quality_annul = compareAnnul(zaiQuanBean, annul)
            if (quality_annul && price_compare && premium_compare && quality_annul && quality_compare && quality_year) {
                list.add(zaiQuanBean)
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun compareQuality(zaiQuanBean: ZaiQuanBean, quality: String?): Boolean {
        var rating_cd = zaiQuanBean.rating_cd

        if (TextUtils.isEmpty(quality)) {
            return true
        }
        if (quality.equals("AAA")) {
            return when (rating_cd) {
                "AAA" -> true
                else -> false
            }
        }
        if (quality.equals("AA+")) {
            return when (rating_cd) {
                "AAA" -> true
                "AA+" -> true
                else -> false
            }
        }
        if (quality.equals("AA")) {
            return when (rating_cd) {
                "AAA" -> true
                "AA+" -> true
                "AA" -> true
                else -> false
            }
        }
        if (quality.equals("AA-")) {
            return when (rating_cd) {
                "AAA" -> true
                "AA+" -> true
                "AA" -> true
                "AA-" -> false
                else -> false
            }
        }
        if (quality.equals("A+")) {
            return when (rating_cd) {
                "AAA" -> true
                "AA+" -> true
                "AA" -> true
                "AA-" -> true
                "A+" -> true
                else -> false
            }
        }

        return false
    }

    private fun comparePrice(zaiQuanBean: ZaiQuanBean, price: String?): Boolean {
        var zaiquan_price = zaiQuanBean.price

        if (TextUtils.isEmpty(price)) {
            return true
        }
        if (zaiquan_price!!.toDouble() <= price!!.toDouble()) {
            return true
        }
        return false
    }

    private fun comparePremium(zaiQuanBean: ZaiQuanBean, premium: String?): Boolean {
        var zaiquan_premium = zaiQuanBean.premium_rt

        if (TextUtils.isEmpty(premium)) {
            return true
        }
        if (zaiquan_premium!!.replace("%", "").toDouble() <= premium!!.toDouble()) {
            return true
        }
        return false
    }

    private fun compareYear(zaiQuanBean: ZaiQuanBean, year: String?): Boolean {
        var zaiquan_year = zaiQuanBean.year_left

        if (TextUtils.isEmpty(year)) {
            return true
        }
        if (zaiquan_year!!.toDouble() <= year!!.toDouble()) {
            return true
        }
        return false
    }

    private fun compareAnnul(zaiQuanBean: ZaiQuanBean, annul: String?): Boolean {
        var zaiquan_ytm_rt_tax = zaiQuanBean.ytm_rt_tax
        if (TextUtils.isEmpty(annul)) {
            return true
        }
        if (zaiquan_ytm_rt_tax!!.replace("%", "").toDouble() >= annul!!.toDouble()) {
            return true
        }
        return false
    }

}