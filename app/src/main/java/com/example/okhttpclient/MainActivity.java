package com.example.okhttpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.okhttpclient.net.BaseBean;
import com.example.okhttpclient.net.OkhttpCallBack;
import com.example.okhttpclient.net.OkhttpClient;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBtn = findViewById(R.id.test_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("expect", "18135");
                params.put("code", "ssq");
                OkhttpClient.getInstance().doGet("/api/lottery/common/aim_lottery", params, new OkhttpCallBack() {

                    @Override
                    public void onResponse(final BaseBean baseBean) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
