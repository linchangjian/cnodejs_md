package com.aniu.cnodejs_md.entity;

import android.text.TextUtils;

import com.aniu.cnodejs_md.utils.FormatUtils;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by aniu on 16/3/3.
 */
public class Reply {
    private String id;

    private Author author;

    private String content;

    @SerializedName("ups")
    private List<String> upList;

    @SerializedName("create_at")
    private DateTime createAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUpList() {
        return upList;
    }

    public void setUpList(List<String> upList) {
        this.upList = upList;
    }

    public DateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }

    // TODO Html渲染缓存
    private String handleContent = null;

    public String getHandleContent() {
        if (handleContent == null) {
            handleContent = FormatUtils.handleHtml(getContent());
        }
        return handleContent;
    }

    public void setHandleContent(String content) {
        handleContent = content;
    }

    public boolean isEmptyContent() {
        return TextUtils.isEmpty(getContent()) || TextUtils.equals(getContent(), "<div class=\"markdown-text\"></div>");
    }
}
