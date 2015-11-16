package com.aniu.cnodejs_md.entity;

public class Result<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}