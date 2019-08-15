package com.caixc.easynoteapp.adapter

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.TemplatureBean
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.lang.Exception

class TemplatureEditAdapter(layoutResId: Int, data: MutableList<TemplatureBean>?) :
    BaseItemDraggableAdapter<TemplatureBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: TemplatureBean?) {
        helper!!.setText(R.id.tv_type, item?.index_type)
            .setText(R.id.et_code_in, item?.code_in)
            .setText(R.id.et_code_out, item?.code_out)
            .addOnClickListener(R.id.tv_temp)

        if (item?.templature == 0.toDouble()) {
            helper!!.setText(R.id.tv_temp, "")
        } else {
            helper!!.setText(R.id.tv_temp, "" + item?.templature)
        }

        helper!!.setBackgroundColor(R.id.ll, mContext.resources.getColor(R.color.grey_text))

        helper!!.getView<EditText>(R.id.et_code_in).addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                data[helper.adapterPosition].code_in = s.toString().trim()
            }
        })

        helper!!.getView<EditText>(R.id.et_code_out).addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                data[helper.adapterPosition].code_out = s.toString().trim()
            }
        })

    }

}