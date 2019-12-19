package com.example.okhttpclient;

import android.app.Application;

import com.example.okhttpclient.net.OkhttpClient;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkhttpClient.getInstance().initOkHttp(" http://www.mxnzp.com");
    }
}
