package com.caixc.easynoteapp.net.service

import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.TemplatureBean
import com.caixc.easynoteapp.bean.UserInfo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface InfoService {

    /**
     * 提交或者更新日志
     */
    @Multipart
    @POST("/api/header")
    fun postImage(@Part partList: List<MultipartBody.Part>): Observable<HttpResult>


    /**
     * 获取长投温度
     */
    @GET("/api/info")
    fun getInfo(): Observable<UserInfo>
}