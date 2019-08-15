package com.caixc.easynoteapp.bean

import java.io.Serializable

data class NoteBean(var id: Int, var content: String, var time: String)

data class InOutComeBean(var create_time: String, var money: Double, var reason: String,var i_id:Long) : Serializable

data class KeyValueBean(var name:String,var value:String)
class ZaiQuanBean {
    var bond_id: String? = null
    var bond_nm: String? = null
    var price: Double? = null
    var stock_nm: String? = null
    var premium_rt: String? = null
    var rating_cd: String? = null
    var year_left: String? = null
    var ytm_rt_tax: String? = null
}

/**
 * bond_id 代码
 * bond_nm  转债名称
 * price 现价
 * stock_nm  正股名称
 * sprice 正股价格
 * sincrease_rt 正股涨跌
 * premium_rt 溢价率
 *      转股代码：128024；2018-06-11 开始转股
 *      转股代码：未到转股期；2019-09-16 开始转股
 * rating_cd 评级
 * year_left 剩余年限
 * ytm_rt_tax 到期税后收益
 *
 */

class TemplatureBean (
    val a_id: Int,
    var code_in: String,
    var code_out: String,
    var index_type: String,
    val create_time:String,
    var templature: Double
) :Serializable

