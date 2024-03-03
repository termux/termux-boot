package com.termux.boot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

public class BootActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // For details see:
        // https://stackoverflow.com/a/63250729/
        // https://www.reddit.com/r/tasker/comments/d7whyj/android_10_and_auto_starting_apps/
        // https://stackoverflow.com/q/64642362/
        // https://stackoverflow.com/a/19856267/
        Context context = getApplicationContext();
        int Q = 29; // Android 10 (the constant is missing here in Build.VERSION for SdkVersion 28)
        if (Build.VERSION.SDK_INT >= Q) {
            if (!Settings.canDrawOverlays(context)) {
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
            }
        }

        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.loadUrl("file:///android_asset/overview.html");
        setContentView(webView);
    }
}
