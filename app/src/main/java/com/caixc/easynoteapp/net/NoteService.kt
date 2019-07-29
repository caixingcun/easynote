package com.caixc.easynoteapp.net

import com.caixc.easynoteapp.bean.HttpErrorBean
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.bean.NoteBean
import io.reactivex.Observable
import retrofit2.http.*


interface NoteService {

    /**
     * 根据id 获取日记详情
     */
    @GET("/api/note/{id}")
    fun getNote(@Path("id") id: Int): Observable<NoteBean>

    /**
     * 提交或者更新日志
     */
    @FormUrlEncoded
    @POST("/api/note")
    fun postNote(@Field("id")id:Int, @Field("content")content:String, @Field("time")time:String):Observable<HttpResult>
    /**
     * 根据日记id删除日记
     */
    @DELETE("/api/note/{id}")
    fun deleteNote(@Path("id") id: Int):Observable<HttpResult>

    /**
     * 获取所有日记
     */
    @GET("/api/notes")
    fun getNotes(): Observable<List<NoteBean>>

}