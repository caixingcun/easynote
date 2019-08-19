package com.caixc.easynoteapp.adapter

import android.widget.ImageView
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.util.ImageUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class PictureAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    var isSelect: Boolean = false


    override fun convert(helper: BaseViewHolder?, item: String?) {
        ImageUtil.loadWithPic(mContext, item!!, helper!!.getView(R.id.iv) as ImageView)

    }

}