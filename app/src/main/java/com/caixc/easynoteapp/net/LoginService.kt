package com.caixc.easynoteapp.net

import com.caixc.easynoteapp.bean.HttpErrorBean
import com.caixc.easynoteapp.bean.HttpResult
import com.caixc.easynoteapp.bean.LoginResultBean
import com.caixc.easynoteapp.bean.NoteBean
import io.reactivex.Observable
import retrofit2.http.*


interface LoginService {
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("account") account: String, @Field("password") password: String): Observable<LoginResultBean>
    /**
     * 登出
     */
    @GET("/api/logout")
    fun  logout():Observable<HttpResult>
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("account") account: String, @Field("password") password: String): Observable<LoginResultBean>

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("/user/changepwd")
    fun changePwd(@Field("account") account: String, @Field("password") password: String,@Field("new_password")newPassword:String): Observable<LoginResultBean>




}