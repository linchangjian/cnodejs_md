package com.aniu.cnodejs_md.api;

import com.aniu.cnodejs_md.BuildConfig;
import com.aniu.cnodejs_md.utils.gson.GsonWrapper;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ApiClient {

    private ApiClient() {}

    private static final String API_HOST = "https://cnodejs.org/api";

    public static final ApiService service = new RestAdapter.Builder()
            .setEndpoint(API_HOST)
            .setConverter(new GsonConverter(GsonWrapper.gson))
            .setRequestInterceptor(new ApiRequestInterceptor())
            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            .build()
            .create(ApiService.class);
}
