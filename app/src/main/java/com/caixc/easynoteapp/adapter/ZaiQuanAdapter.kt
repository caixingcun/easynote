package com.caixc.easynoteapp.adapter

import android.graphics.Color
import com.caixc.easynoteapp.R
import com.caixc.easynoteapp.bean.ZaiQuanBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ZaiQuanAdapter(layoutResId: Int, data: MutableList<ZaiQuanBean>?) :
    BaseQuickAdapter<ZaiQuanBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: ZaiQuanBean?) {
        helper!!
            .setText(R.id.bond_id, item!!.bond_id)  //转债id
            .setText(R.id.bond_nm, item!!.bond_nm)  //转债名称
            .setText(R.id.price, "" + item!!.price) //转债价格
//            .setText(R.id.stock_nm, item!!.stock_nm) // 正股名称
            .setText(R.id.premium_rt, item!!.premium_rt) // 溢价率
            .setText(R.id.rating_cd, item!!.rating_cd)  //评级
            .setText(R.id.year_left, item!!.year_left) // 剩余年限
            .setText(R.id.ytm_rt_tax, item!!.ytm_rt_tax)  //到期收益率


        if (item!!.premium_rt!!.replace("%", "").toDouble() <= 2) { //进攻
            helper!!.setBackgroundColor(R.id.tv_star, mContext.resources.getColor(R.color.green))
        }else if (item!!.ytm_rt_tax!!.replace("%", "").toDouble() > 3) { //防御
            helper!!.setBackgroundColor(R.id.tv_star, mContext.resources.getColor(R.color.yellow))
        } else if (item!!.premium_rt!!.replace("%", "").toDouble() <= 5 &&item!!.ytm_rt_tax!!.replace("%", "").toDouble() > 0) { // 平衡
            helper!!.setBackgroundColor(R.id.tv_star, mContext.resources.getColor(R.color.blue))
        } else {
            helper!!.setBackgroundColor(R.id.tv_star, mContext.resources.getColor(R.color.white))
        }




    }
}