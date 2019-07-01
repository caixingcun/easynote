package com.caixc.easynoteapp.retrofit

import com.caixc.easynoteapp.bean.LoginBean
import com.caixc.easynoteapp.bean.LoginResultBean
import io.reactivex.Observable



import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitService{
    @POST("/user/login")
    fun login (@Body body: String):Observable<LoginResultBean>

}