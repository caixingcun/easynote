package com.caixc.easynoteapp.net.service

import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.TemplatureBean
import com.caixc.easynoteapp.bean.UserInfo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PicService {


    /**
     * 获取长投温度
     */
    @GET("/api/pics/dirs")
    fun getPicDir(): Observable<MutableList<String>>

    /**
     * 获取长投温度
     */
    @GET("/api/pics/dir/{dirs}")
    fun getPics(@Path("dirs") dirs: String): Observable<List<String>>

    /**
     * 获取长投温度
     */
    @GET("/api/is_admin")
    fun getIsAdmin(): Observable<HttpResult>


}