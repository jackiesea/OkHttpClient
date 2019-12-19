package com.example.okhttpclient.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpRequest<T> {
    private static long TIMEOUT_CONNECT = 5L;
    private static long TIMEOUT_READ = 20L;
    private static long TIMEOUT_WRITE = 10L;

    private volatile String baseUrl;
    public static OkHttpClient client = null;

    OkhttpRequest(String baseUrl) {
        if (this.baseUrl == null) {
            this.baseUrl = baseUrl;
        }
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }
    }

    //格式化url
    private StringBuffer formatUrl(String path) {
        return new StringBuffer(this.baseUrl + (path == null ? "" : path));
    }

    //统一的header
    private Request.Builder formatHeader(Request.Builder requestBuilder) {
        for (Map.Entry<String, String> e : Header.getHeaderParams().entrySet()) {
            if (!TextUtils.isEmpty(e.getValue())) {
                requestBuilder.addHeader(e.getKey(), e.getValue());
            }
        }
        return requestBuilder;
    }

    //请求数据回调
    private void doCallBack(Request request, final OkhttpCallBack callBack) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 404 && callBack != null) {
                    String value = response.body().string();
                    BaseBean<T> bean = new Gson().fromJson(value, new TypeToken<BaseBean>() {
                    }.getType());
                    Log.d("onResponse", bean.toString());
                    callBack.onResponse(bean);
                }
            }
        });
    }

    //get请求
    public void doGet(String path, Map<String, String> params, final OkhttpCallBack callBack) {
        StringBuffer url = formatUrl(path);
        if (params != null) {
            url.append("?");
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> e = (Map.Entry) iterator.next();
                url.append(e.getKey()).append("=").append(e.getValue() + "&");
            }
        }
        Request.Builder requestBuilder = new Request.Builder().url(url.toString());
        Request request = formatHeader(requestBuilder).build();
        doCallBack(request, callBack);
    }

    //post请求-map
    public void doPost(String path, Map<String, String> params, final OkhttpCallBack callBack) {
        StringBuffer url = formatUrl(path);
        FormBody.Builder formbody = new FormBody.Builder();
        if (null != params) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry) iterator.next();
                formbody.add(elem.getKey(), elem.getValue());
            }
        }

        RequestBody body = formbody.build();
        Request.Builder requestBuilder = new Request.Builder().url(url.toString());
        Request request = formatHeader(requestBuilder).post(body).build();
        doCallBack(request, callBack);
    }

    //post请求-content
    public void doPost(String path, String content, final OkhttpCallBack callBack) {
        StringBuffer url = formatUrl(path);
        RequestBody body = FormBody.create(MediaType.parse("application/json"), content);
        Request.Builder requestBuilder = new Request.Builder().url(url.toString());
        Request request = formatHeader(requestBuilder).post(body).build();
        doCallBack(request, callBack);
    }

    //put请求
    public void doPut(String path, Map<String, String> params, final OkhttpCallBack callBack) {
        StringBuffer url = formatUrl(path);
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> elem = (Map.Entry) iterator.next();
            builder.add(elem.getKey(), elem.getValue());
        }

        RequestBody body = builder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url.toString());
        Request request = formatHeader(requestBuilder).put(body).build();
        doCallBack(request, callBack);
    }
}
