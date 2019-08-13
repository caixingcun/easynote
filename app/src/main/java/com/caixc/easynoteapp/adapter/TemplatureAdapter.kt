package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.bean.NoteBean
import com.caixc.easynoteapp.bean.TemplatureBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class TemplatureAdapter(layoutResId: Int, data: MutableList<TemplatureBean>?) :
    BaseQuickAdapter<TemplatureBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: TemplatureBean?) {
        helper!!.setText(R.id.tv_type, item?.index_type)
            .setText(R.id.tv_code_in, item?.code_in)
            .setText(R.id.tv_code_out, item?.code_out)
            .setText(R.id.tv_temp, "" + item?.templature)

        when {
            item!!.templature <= 30 -> helper!!.setBackgroundColor(R.id.ll, mContext.resources.getColor(R.color.blue))
            item!!.templature < 40 -> helper!!.setBackgroundColor(R.id.ll, mContext.resources.getColor(R.color.yellow))
            else -> helper!!.setBackgroundColor(R.id.ll, mContext.resources.getColor(R.color.orange))
        }


    }

}