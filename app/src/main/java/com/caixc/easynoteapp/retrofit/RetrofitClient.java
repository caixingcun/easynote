package com.caixc.easynoteapp.retrofit;

import com.caixc.easynoteapp.BuildConfig;
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
                request.newBuilder()
                        .header("token", "")
                        .header("UserAgent", "android")
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(request);
            }
        };
    }

    private static OkHttpClient createOkHttpClient() {
        HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.INSTANCE.getSslSocketFactory();
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory.getSSLSocketFactory(), sslSocketFactory.getTrustManager())
                .hostnameVerifier(getTrustAllVerifier())
                .addInterceptor(createRequestIntercept())  //过滤器
                .addInterceptor(createLogIntercept()) //log日志过滤器
                .build();

    }

    private static Interceptor createLogIntercept() {
        if (BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return null;
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
