package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.bean.NoteBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class HomeAdapter(layoutResId: Int, data: MutableList<NoteBean>?) : BaseQuickAdapter<NoteBean, BaseViewHolder>(layoutResId, data) {

    var isSelect: Boolean = false


    override fun convert(helper: BaseViewHolder?, item: NoteBean?) {
        helper!!.setText(R.id.tv_title, item?.content)
            .setText(R.id.tv_date, item?.time)
            .setVisible(R.id.cb, isSelect)

    }

}