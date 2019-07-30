package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.bean.NoteBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class HomeModuleAdapter(layoutResId: Int, data: MutableList<Pair<String,Int>>?) : BaseQuickAdapter<Pair<String,Int>, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: Pair<String,Int>?) {
        helper!!.setText(R.id.tv, item!!.first)
            .setImageResource(R.id.iv, item.second)

    }

}