package com.caixc.easynoteapp.net.service

import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.InOutComeBean
import com.caixc.easynoteapp.bean.NoteBean
import io.reactivex.Observable
import retrofit2.http.*

interface InOutComeService {

    /**
     * 提交或者更新收支记录
     */
    @FormUrlEncoded
    @POST("/api/in-out-come")
    fun postInOutCome(@Field("id")id:Long, @Field("money")content:Double, @Field("reason")reason:String,@Field("create_time")create_time:String): Observable<HttpResult>
    /**
     * 根据日记id删除收支记录
     */
    @DELETE("/api/in-out-come/{id}")
    fun deleteInOutCome(@Path("id") id: Long): Observable<HttpResult>

    /**
     * 获取所有收支记录
     */
    @GET("/api/in-out-come")
    fun getInOutComes(): Observable<List<InOutComeBean>>

}