package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.InOutComeBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class HomeAdapter(layoutResId: Int, data: MutableList<InOutComeBean>?) : BaseQuickAdapter<InOutComeBean, BaseViewHolder>(layoutResId, data) {

    var isSelect: Boolean = false


    override fun convert(helper: BaseViewHolder?, item: InOutComeBean?) {
        helper!!.setText(R.id.tv_title, item?.reason)
            .setText(R.id.tv_date, item?.create_time)
            .setVisible(R.id.cb, isSelect)

    }

}