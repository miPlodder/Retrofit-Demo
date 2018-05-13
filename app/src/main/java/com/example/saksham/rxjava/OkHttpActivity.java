package com.example.saksham.rxjava;

/*
 * Created by saksham on 12/May/2018
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnFetch;
    TextView tvFetch;
    OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        
        btnFetch = findViewById(R.id.btn_fetch);
        btnFetch.setOnClickListener(this);

        tvFetch = findViewById(R.id.tv_fetch);

    }

    @Override
    public void onClick(View v) {
        final Request request = new Request.Builder().url("https://reqres.in/api/users/1").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            try {
                                tvFetch.setText(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            tvFetch.append("\nrequest"+request);
                        }
                        else {
                            tvFetch.setText("Error");
                        }
                    }
                });
            }
        });
    }
}
