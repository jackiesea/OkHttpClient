package com.example.okhttpclient.net;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class BaseBean<T> implements ResponseProtocol<T> {
    protected String msg;
    protected Integer code;
    protected T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int code() {
        return code == null ? -1 : 0;
    }

    @Override
    public String message() {
        return msg;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
