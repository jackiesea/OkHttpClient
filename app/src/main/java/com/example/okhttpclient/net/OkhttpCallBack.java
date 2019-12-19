package com.example.okhttpclient.net;

public interface OkhttpCallBack<T> {
    void onResponse(BaseBean<T> baseBean);
}
