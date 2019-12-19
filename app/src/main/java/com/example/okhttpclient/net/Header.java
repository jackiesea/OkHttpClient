package com.example.okhttpclient.net;

import java.util.HashMap;
import java.util.Map;

public class Header {
    private static final String APP_TOKEN = "token";
    private static final String UCODE = "ucode";

    public static Map<String, String> getHeaderParams() {
        Map<String, String> headers = new HashMap<>();
        headers.put(APP_TOKEN, "mytoken");
        headers.put(UCODE, "myucode");
        return headers;
    }
}
