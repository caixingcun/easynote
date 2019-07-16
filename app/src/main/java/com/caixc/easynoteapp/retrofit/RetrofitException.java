package com.caixc.easynoteapp.retrofit;

import android.net.ParseException;
import android.text.TextUtils;
import com.caixc.easynoteapp.bean.HttpErrorBean;
import com.caixc.easynoteapp.exception.ResponeThrowable;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import org.json.JSONException;
import retrofit2.Response;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class RetrofitException {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable retrofitException(Throwable e) {
        ResponeThrowable ex;
        String message;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            Response<?> response = httpException.response();
            String msg = getHttpMessage(response);
            if (!TextUtils.isEmpty(msg)) {
                ex = new ResponeThrowable(e, httpException.code(), msg);
                return ex;
            }
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    message = "未授权";
                    break;
                case FORBIDDEN:
                    message = "身份标识过期，请重新登录";
                    break;
                case NOT_FOUND:
                    message = "路径不存在";
                    break;
                case REQUEST_TIMEOUT:
                    message = "请求超时";
                    break;
                case GATEWAY_TIMEOUT:
                    message = "网关超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                    message = "服务器出错";
                    break;
                case BAD_GATEWAY:
                    message = "网关出错";
                    break;
                case SERVICE_UNAVAILABLE:
                    message = "服务器不可用";
                    break;
                default:
                    message = "网络错误";
                    break;
            }
            ex = new ResponeThrowable(e, httpException.code(), message);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            message = "解析错误";
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR, message);
            return ex;
        } else if (e instanceof ConnectException
                || e instanceof SocketTimeoutException
                || e instanceof UnknownHostException) {
            message = "连接失败";
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR, message);
            return ex;
        } else if (e instanceof SSLHandshakeException) {
            message = "证书验证失败";
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR, message);
            return ex;
        } else {
            message = "未知错误";
            ex = new ResponeThrowable(e, ERROR.UNKNOWN, message);
            return ex;
        }
    }

    private static String getHttpMessage(Response<?> response) {
        try {
        String s = response.errorBody().string();
            HttpErrorBean httpErrorBean = new Gson().fromJson(s, HttpErrorBean.class);
            return httpErrorBean.getMessage();
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
    }

}


