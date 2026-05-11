package com.example.movies_app2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class activityvideoplayer extends AppCompatActivity {

    WebView webView;
    String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activityvideoplayer);

        // Get video URL from Intent
        videoUrl = getIntent().getStringExtra("videoUrl");

        webView = findViewById(R.id.webview);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (videoUrl != null) {
            webView.loadUrl(videoUrl);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (webView != null && videoUrl != null) {
            webView.loadUrl(videoUrl);
        }
    }
}