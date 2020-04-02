package my.upgrade007.upgrade;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.upgrade.R;
import com.github.ybq.android.spinkit.style.CubeGrid;

public class GetLinksDetails extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_links_details);
        refreshLayout = findViewById(R.id.refreshlayOfLinkDetails);

        progressBar = findViewById(R.id.pbOfLinksDetails);
        CubeGrid cubeGrid = new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
        setWebView();
        final String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               webView.loadUrl(url);
            }
        });

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView()
    {
        webView = findViewById(R.id.wvOfLinksDetails);
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
                webView.setVisibility(View.INVISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
