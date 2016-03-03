package com.aniu.cnodejs_md.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aniu on 16/3/3.
 */
public class TopicWithReply extends Topic{

    @SerializedName("replies")
    private List<Reply> replyList;

    public List<Reply> getReplyList(){
        return replyList;
    }

    public void setReplyList(List<Reply> replyList){
        this.replyList = replyList;
    }
}
