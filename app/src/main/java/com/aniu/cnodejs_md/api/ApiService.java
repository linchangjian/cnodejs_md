package com.aniu.cnodejs_md.api;

import com.aniu.cnodejs_md.entity.Result;
import com.aniu.cnodejs_md.entity.TabType;
import com.aniu.cnodejs_md.entity.Topic;
import com.aniu.cnodejs_md.entity.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiService {

    @GET("/v1/topics")
    void getTopicList(
            @Query("tab") TabType tab,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query("mdrender") Boolean mdrender,
            Callback<Result<List<Topic>>> callback
    );

    @GET("/v1/user/{loginName}")
    void getUser(
            @Path("loginName") String loginName,
            Callback<Result<User>> callback
    );

}