package com.termux.boot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

public class BootActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.loadUrl("file:///android_asset/overview.html");
        setContentView(webView);
    }

}
