package com.learnyourself.learn.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.learnyourself.learn.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
public static int APP_CACHE_DIRNAME = 1;
String myurl = "https://cloudgames.com/games/html5/mini-o-stars-new-en-s-iga-cloud/index.html?pub=10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView)findViewById(R.id.webviewone);
        WebSettings settings = webView.getSettings();
        WebViewClientImpl webViewClient = new WebViewClientImpl(WebViewActivity.this);
        webView.setWebViewClient(webViewClient);
        webView.requestFocusFromTouch();
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        settings.setAppCachePath(cacheDirPath);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(myurl);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}
