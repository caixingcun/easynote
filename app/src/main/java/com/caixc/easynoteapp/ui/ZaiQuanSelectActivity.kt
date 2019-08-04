package com.caixc.easynoteapp.ui

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_select_zhuanzhai.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.lang.Exception


class ZaiQuanSelectActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_select_zhuanzhai

    var quality = "AA"
    var price = ""
    var premium = ""
    var year = "5.5"
    var annul = ""


    override fun initView() {
        tv_title.text = "转债筛选参数"
        tv_right.text = "清空"
        tv_right.visibility = View.VISIBLE
    }

    override fun initListener() {
        spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (resources.getStringArray(R.array.zhuanzhai_types)[position]) {
                    "进攻型可转债" -> {
                        spinner_quality.setSelection(0)
                        et_price.setText("110")
                        et_premium.setText("2")
                        et_annul.setText("")
                        et_year.setText("5.5")
                    }
                    "平衡型可转债" -> {
                        spinner_quality.setSelection(0)
                        et_price.setText("110")
                        et_premium.setText("5")
                        et_annul.setText("0")
                        et_year.setText("5.5")
                    }

                    "防御型可转债" -> {
                        spinner_quality.setSelection(0)
                        et_price.setText("100")
                        et_premium.setText("")
                        et_annul.setText("3")
                        et_year.setText("5.5")
                    }

                }
            }
        }


        spinner_quality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                quality = resources.getStringArray(R.array.zhuanzhai_qualitys)[position]
            }
        }

        iv_left.setOnClickListener {
            finish()
        }
        tv_right.setOnClickListener {
            et_annul.setText("")
            et_premium.setText("")
            et_price.setText("")
            et_year.setText("")
        }

        btn_query.setOnClickListener {
            if (!emptyCheck()) {
                return@setOnClickListener
            }
            var intent = Intent()
            intent.putExtra("quality", quality)
//            intent.putExtra("price", price)
            intent.putExtra("premium", premium)
            intent.putExtra("year", year)
            intent.putExtra("annul", annul)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun emptyCheck(): Boolean {
        quality = resources.getStringArray(R.array.zhuanzhai_qualitys)[spinner_quality.selectedItemPosition]
        price = et_price.text.toString().trim()
        premium = et_premium.text.toString().trim()
        year = et_year.text.toString().trim()
        annul = et_annul.text.toString().trim()

        if (!TextUtils.isEmpty(price)) {
            try {
                price.toDouble()
            } catch (e: Exception) {
                toast("当前价格格式错误")
                return false
            }
        }

        if (!TextUtils.isEmpty(premium)) {
            try {
                premium.replace("%", "").toDouble()
            } catch (e: Exception) {
                toast("溢价率格式错误")
                return false
            }
        }


        if (!TextUtils.isEmpty(year)) {
            try {
                year.toDouble()
            } catch (e: Exception) {
                toast("年限格式错误")
                return false
            }
        }

        if (!TextUtils.isEmpty(annul)) {
            try {
                annul.replace("%", "").toDouble()
            } catch (e: Exception) {
                toast("年化收益格式错误")
                return false
            }
        }

        return true
    }

}
