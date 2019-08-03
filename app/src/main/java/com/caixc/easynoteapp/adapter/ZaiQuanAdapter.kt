package com.caixc.easynoteapp.adapter

import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.ZaiQuanBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ZaiQuanAdapter(layoutResId: Int, data: MutableList<ZaiQuanBean>?) : BaseQuickAdapter<ZaiQuanBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: ZaiQuanBean?) {
        helper!!
            .setText(R.id.bond_id, item!!.bond_id)
            .setText(R.id.bond_nm, item!!.bond_nm)
            .setText(R.id.price, "" + item!!.price)
            .setText(R.id.stock_nm, item!!.stock_nm)
            .setText(R.id.premium_rt, item!!.premium_rt)
            .setText(R.id.rating_cd, item!!.rating_cd)
            .setText(R.id.year_left, item!!.year_left)
            .setText(R.id.ytm_rt_tax, item!!.ytm_rt_tax)

    }
}