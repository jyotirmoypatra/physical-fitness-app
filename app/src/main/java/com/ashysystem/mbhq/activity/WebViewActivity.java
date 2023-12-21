package com.ashysystem.mbhq.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ashysystem.mbhq.R;

public class WebViewActivity extends Activity {

    ImageView imgLeftBack;
    WebView customWebview;
    String WEBVIEWURL = "";
    ProgressDialog prDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        imgLeftBack = (ImageView) findViewById(R.id.imgLeftBack);
        customWebview = (WebView) findViewById(R.id.customWebview);

        if (getIntent() != null && getIntent().hasExtra("WEBVIEWURL")) {
            WEBVIEWURL = getIntent().getStringExtra("WEBVIEWURL");
        }

        imgLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        customWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        customWebview.setWebViewClient(new MyWebViewClient());

        if (!WEBVIEWURL.equals("")) {
            customWebview.loadUrl(WEBVIEWURL);
        } else {
            finish();
        }

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            try {
                prDialog = new ProgressDialog(WebViewActivity.this);
                prDialog.setMessage("Please wait ...");
                prDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                if (prDialog != null) {
                    prDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
