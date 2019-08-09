package com.caixc.easynoteapp.net.service

import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.LoginResultBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 反馈接口
 */
internal interface FeedBackService {
    /**
     *  反馈提交
     *  @param phone 联系方式
     *  @param content 提交内容
     */
    @FormUrlEncoded
    @POST("/user/feedback")
    fun submitFeedBack(@Field("phone") phone: String, @Field("content") content: String):Observable<HttpResult>

}