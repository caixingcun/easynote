package com.caixc.easynoteapp.retrofit

import android.util.Log
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.global.App
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

     fun create(baseUrl: String): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                val requestUrl = request.url().toString()

                if ((requestUrl.contains("/login") || requestUrl.contentEquals("/register"))
                    && !response.headers("COOKIE").isEmpty()
                ) {
                    val token = response.header("token")
                    Preference().setValue("token", token)

                }
                response
            }
            addInterceptor {
                val request = it.request()
                val newBuilder = request.newBuilder()
                newBuilder.addHeader("token", Preference().getValue("token", ""))
                it.proceed(request)
            }

            if (App.debug) {
                addInterceptor(
                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("tag", it) }).apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }


        }.build()

        return Retrofit.Builder().apply {

            baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        }.build()

    }
}

