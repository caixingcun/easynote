package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.bean.NoteBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class InOutComeAdapter(layoutResId: Int, data: MutableList<InOutComeBean>?) :
    BaseQuickAdapter<InOutComeBean, BaseViewHolder>(layoutResId, data) {

    var isSelect: Boolean = false


    override fun convert(helper: BaseViewHolder?, item: InOutComeBean?) {
        helper!!.setText(R.id.tv_reason, item?.reason)
            .setText(R.id.tv_date, item?.create_time)
            .setImageResource(R.id.iv_type, if (item!!.money > 0) R.drawable.ic_in else R.drawable.ic_out)
            .setText(R.id.tv_money, "" + item?.money)
    }

}