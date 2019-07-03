package com.caixc.easynoteapp.retrofit

import com.caixc.easynoteapp.bean.GankBean
import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.bean.LoginResultBean
import io.reactivex.Observable
import okhttp3.RequestBody


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface RetrofitService {
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("/user/login")
    fun login(@Body body: RequestBody): Observable<LoginResultBean>

    @GET("/api/data/福利/10/1")
    fun gank() :Observable<GankBean>
}