package com.caixc.easynoteapp.convert;


import com.caixc.easynoteapp.bean.HttpResult;
import com.caixc.easynoteapp.exception.ApiError;
import com.caixc.easynoteapp.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CheckGsonBodyConvert<T> implements Converter<ResponseBody,T> {

    private final TypeAdapter<T> adapter;
    private final Gson mGson;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    CheckGsonBodyConvert(TypeAdapter<T> adapter) {
        this.mGson = new Gson();
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpResult result = mGson.fromJson(response, HttpResult.class);
        int responseCode = result.getCode();
        //如果响应码不为正常响应我们就抛出一个自定义的异常，onError方法回去捕获这个异常
        if (!(responseCode == 0)) {
            value.close();
            throw new ApiException(responseCode, ApiError.getErrorMessage(responseCode));
        }
        MediaType type=value.contentType();
        Charset charset=type!=null?type.charset(UTF_8):UTF_8;
        InputStream is = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(is,charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }

    }
}
