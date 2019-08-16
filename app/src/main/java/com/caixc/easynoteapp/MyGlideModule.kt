package com.caixc.easynoteapp

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.caixc.easynoteapp.util.HttpsUtils
import okhttp3.OkHttpClient

import java.io.InputStream
import java.util.concurrent.TimeUnit

@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

        val okhttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true) // 设置出现错误进行重新连接。
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .sslSocketFactory(
                HttpsUtils.sslSocketFactory.sSLSocketFactory!!,
                HttpsUtils.sslSocketFactory.trustManager!!
            )
            .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
            .build()

        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okhttpClient))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
