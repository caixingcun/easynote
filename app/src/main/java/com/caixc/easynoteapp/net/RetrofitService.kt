package com.caixc.easynoteapp.net

import com.caixc.easynoteapp.bean.LoginResultBean
import io.reactivex.Observable
import retrofit2.http.*


interface RetrofitService {
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("account") account: String, @Field("password") password: String): Observable<LoginResultBean>

}