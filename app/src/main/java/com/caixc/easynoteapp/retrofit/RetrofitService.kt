package com.caixc.easynoteapp.retrofit

import com.caixc.easynoteapp.bean.GankBean
import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.bean.LoginResultBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*


interface RetrofitService {
    @POST("/user/login")
    fun login(@Body body: RequestBody): Observable<LoginResultBean>

    @GET("/api/data/福利/10/1")
    fun gank() :Observable<GankBean>

}