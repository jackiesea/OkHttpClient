package com.example.okhttpclient.net;

public interface ResponseProtocol<T> {

    int code();

    String message();

    T getData();

}
