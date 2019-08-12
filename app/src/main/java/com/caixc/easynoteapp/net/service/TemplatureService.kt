package com.caixc.easynoteapp.net.service

import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.TemplatureBean
import io.reactivex.Observable
import retrofit2.http.*

interface TemplatureService {

    /**
     * 根据id 获取日记详情
     */
    @GET("/api/indexs")
    fun getIndexs(): Observable<List<String>>

    /**
     * 提交或者更新日志
     */
    @FormUrlEncoded
    @POST("/api/temperature")
    fun postTemplature(@Field("create_time")create_time:String,
                 @Field("index_type")index_type:String,
                 @Field("temperature")template:Double,
                 @Field("code_in")code_in:String,
                 @Field("code_out")code_out:String
                 ):Observable<HttpResult>

    /**
     * 获取长投温度
     */
    @GET("/api/temperatures") fun getTemplate():Observable<List<TemplatureBean>>

}
