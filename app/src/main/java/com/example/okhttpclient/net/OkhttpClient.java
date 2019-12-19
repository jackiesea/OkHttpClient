package com.example.okhttpclient.net;

import java.util.Map;

public class OkhttpClient<T> {
    private static OkhttpClient INSTANCE;
    private static OkhttpRequest request = null;

    public static OkhttpClient getInstance() {
        if (INSTANCE == null) {
            synchronized (OkhttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkhttpClient();
                }
            }
        }
        return INSTANCE;
    }

    public void initOkHttp(String baseUrl) {
        if (request == null) {
            request = new OkhttpRequest<T>(baseUrl);
        }
    }

    //get请求
    public void doGet(String path, Map<String, String> params, final OkhttpCallBack callBack) {
        request.doGet(path, params, callBack);
    }

    //post请求-map
    public void doPost(String path, Map<String, String> params, final OkhttpCallBack callBack) {
        request.doPost(path, params, callBack);
    }

    //post请求-content
    public void doPost(String path, String content, final OkhttpCallBack callBack) {
        request.doPost(path, content, callBack);
    }

    //put请求
    public void doPut(String path, Map<String, String> params, final OkhttpCallBack callBack) {
        request.doPut(path, params, callBack);
    }
}
