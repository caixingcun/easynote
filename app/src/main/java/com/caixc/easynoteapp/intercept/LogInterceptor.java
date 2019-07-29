package com.caixc.easynoteapp.intercept;

import android.text.TextUtils;
import com.caixc.easynoteapp.util.LogUtils;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

/**
 * Created by ccb on 2017/9/23.
 */

public class LogInterceptor implements Interceptor {
    public static final String TAG = "lai";
    private String tag;
    private boolean mShowLog;

    public LogInterceptor(String tag, boolean showLog) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.mShowLog = showLog;
        this.tag = tag;
    }

    public LogInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mShowLog) {
            logForRequest(request);
        }
        Response response = chain.proceed(request);
        if (mShowLog) {
            response = logForResponse(response);
        }
        return response;
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log

            LogUtils.debug("========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogUtils.debug("url : " + clone.request().url());
            LogUtils.debug("code : " + clone.code());
            LogUtils.debug("protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                LogUtils.debug("message : " + clone.message());

            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    LogUtils.debug("responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        LogUtils.error("responseBody's content : " + resp);
                        LogUtils.printJson(tag, resp, "responseBody's content : ");

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        LogUtils.debug("responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtils.debug("========response'log=======end");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogUtils.debug("========request'log=======");
            LogUtils.debug("method : " + request.method());
            LogUtils.debug("url : " + url);
            if (headers != null && headers.size() > 0) {
                LogUtils.debug("headers : " + headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtils.debug("requestBody's contentType : " + mediaType.toString());
                        LogUtils.debug("requestBody's content : " + bodyToString(request));
                }
            }
            LogUtils.debug("========request'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
