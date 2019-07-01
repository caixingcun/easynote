package com.caixc.easynoteapp.retrofit

import com.caixc.easynoteapp.bean.LoginBean
import okhttp3.Response


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService{
    @POST("/user/login")
    fun<T>  login (@Body body: String)

}