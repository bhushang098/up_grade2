package com.example.upgrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.CubeGrid;

public class NewsDetails extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        webView = findViewById(R.id.wvOfNewsDetails);

        progressBar = findViewById(R.id.spinBarDetailedNewsActivity);
        CubeGrid cubeGrid = new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);

        startWebView();

        webView.loadUrl(getIntent().getStringExtra("url"));

    }


    private void startWebView()
    {
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setVisibility(View.INVISIBLE);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },1500);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
