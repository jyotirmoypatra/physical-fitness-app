package com.ashysystem.mbhq.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashysystem.mbhq.R;


/**
 * Created by android-krishnendu on 9/19/16.
 */
public class PlaySoundActivity extends Activity {

    private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    String soundUrl="";
    ImageView imgLeftBack;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_playsound);

        this.webview = (WebView)findViewById(R.id.webview);
        imgLeftBack=(ImageView)findViewById(R.id.imgLeftBack);

        soundUrl=getIntent().getStringExtra("SOUNDURL");
        Log.e("SOUNDURL",soundUrl+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        imgLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.loadUrl("");
                PlaySoundActivity.this.finish();
                /*Intent intent=new Intent(PlaySoundActivity.this,MainActivity.class);
                startActivity(intent);*/
            }
        });

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(PlaySoundActivity.this, null, "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(PlaySoundActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webview.loadUrl(soundUrl);
        if(soundUrl.equals(""))
        {
            Toast.makeText(PlaySoundActivity.this,"Can not load the page",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        webview.loadUrl("");
        PlaySoundActivity.this.finish();
    }


}
