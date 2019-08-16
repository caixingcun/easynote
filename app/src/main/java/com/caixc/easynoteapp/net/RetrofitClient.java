package com.caixc.easynoteapp.net;

import com.caixc.easynoteapp.BuildConfig;
import com.caixc.easynoteapp.base.Preference;
import com.caixc.easynoteapp.constant.Constant;
import com.caixc.easynoteapp.global.App;
import com.caixc.easynoteapp.global.Constants;
import com.caixc.easynoteapp.intercept.LogInterceptor;
import com.caixc.easynoteapp.util.HttpsUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    public Retrofit getInstance(String baseUrl) {
        return createRetrofit(baseUrl);
    }

    private static Interceptor createRequestIntercept() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request request1 = request.newBuilder()
                        .header("token", Preference.preferences.getString(Constants.TOKEN, ""))
                        .header("UserAgent", "android")
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(request1);
            }
        };
    }

    public static OkHttpClient createOkHttpClient() {
        HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.INSTANCE.getSslSocketFactory();
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory.getSSLSocketFactory(), sslSocketFactory.getTrustManager())
                .hostnameVerifier(getTrustAllVerifier())
                .addInterceptor(createRequestIntercept())  //过滤器
                .addInterceptor(new LogInterceptor("tag",App.debug)) //log日志过滤器
                .build();

    }


    private static HostnameVerifier getTrustAllVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
