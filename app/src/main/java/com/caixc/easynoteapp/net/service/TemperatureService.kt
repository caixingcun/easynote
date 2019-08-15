package com.caixc.easynoteapp.net.service

import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.TemplatureBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface TemperatureService {

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
    fun postTemplature(
        @Field("create_time") create_time: String,
        @Field("index_type") index_type: String,
        @Field("temperature") template: Double,
        @Field("code_in") code_in: String,
        @Field("code_out") code_out: String
    ): Observable<HttpResult>
    /**
     * 提交或者更新日志
     */
    @POST("/api/temperatures")
    fun postTemplatures(@Body list: RequestBody): Observable<HttpResult>


    /**
     * 获取长投温度
     */
    @GET("/api/temperature/{create_time}")
    fun getTemplate(@Path("create_time") create_time: String): Observable<List<TemplatureBean>>

    /**
     * 获取所有温度
     */
    @GET("/api/temperatures")
    fun getTemplates(): Observable<List<TemplatureBean>>
}
