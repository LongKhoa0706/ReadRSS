package com.example.readrss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        webView = findViewById(R.id.webtintuc);
        Intent intent = getIntent();
        String  link = intent.getStringExtra("linktintuc");
        webView.loadUrl(link);
        // ghi click vào đường dẫn chạy trong app của mình
        // không cho đường dẫn chạy ngoài trình duyệt
        webView.setWebViewClient(new WebViewClient());

    }
}
