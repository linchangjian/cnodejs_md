package com.aniu.cnodejs_md.api;

import com.aniu.cnodejs_md.entity.Result;
import com.aniu.cnodejs_md.entity.TabType;
import com.aniu.cnodejs_md.entity.Topic;
import com.aniu.cnodejs_md.entity.TopicUpInfo;
import com.aniu.cnodejs_md.entity.TopicWithReply;
import com.aniu.cnodejs_md.entity.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @GET("/v1/topic/{id}")
    void getTopic(
            @Path("id") String id,
            @Query("mdrender") Boolean mdrender,
            Callback<Result<TopicWithReply>> callback
    );

    @FormUrlEncoded
    @POST("/v1/reply/{replyId}/ups")
    void upTopic(
      @Field("accesstoken") String accessToken,
      @Path("replyId") String replyId,
      Callback<TopicUpInfo> callback
    );

}