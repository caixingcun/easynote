package com.caixc.easynoteapp.retrofit;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RetrofitUtils {
    public static RequestBody convertJson2RequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), json);
    }
}
