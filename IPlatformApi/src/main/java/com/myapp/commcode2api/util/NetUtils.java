package com.myapp.commcode2api.util;

import com.myapp.commcode2api.exception.BusinessException;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class NetUtils {
    public static String senJsonPost(String url,String jsonStr) throws Exception{
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120,TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .build();
        Response response = null;
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonStr);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            response = httpClient.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            throw new BusinessException("senJsonPost error");
        }finally {
            if (response != null){
                response.close();
            }
        }
    }
}
