package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.TemplatureBean
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder

class TemplatureEditAdapter(layoutResId: Int, data: MutableList<TemplatureBean>?) :
    BaseItemDraggableAdapter<TemplatureBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: TemplatureBean?) {
        helper!!.setText(R.id.tv_type, item?.index_type)
            .setText(R.id.et_code_in, item?.code_in)
            .setText(R.id.et_code_out, item?.code_out)

        if (item?.templature == 0.toDouble()) {
            helper!!.setText(R.id.et_temp, "")
        }else{
            helper!!.setText(R.id.et_temp, ""+item?.templature)
        }
        helper!!.setBackgroundColor(R.id.ll, mContext.resources.getColor(R.color.grey_text))


    }

}