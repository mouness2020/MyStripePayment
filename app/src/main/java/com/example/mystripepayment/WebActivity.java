package com.example.mystripepayment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class WebActivity extends AppCompatActivity {

    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("url")){
            url = getIntent().getStringExtra("url");
        }

        WebView webView = (WebView) findViewById(R.id.webview);
        if(url != null){

//            String clientSecret = intent.getData().getQueryParameter(QUERY_CLIENT_SECRET);
//            String sourceId = intent.getData().getQueryParameter(QUERY_SOURCE_ID);
//            if (clientSecret != null
//                    && sourceId != null
//                    && clientSecret.equals(mRedirectSource.getClientSecret())
//                    && sourceId.equals(mRedirectSource.getId())) {
                // Then this is a redirect back for the original source.
                // You should poll your own backend to update based on
                // source status change webhook events it may receive, and display the results
                // of that here.
            //}
            webView.loadUrl(url);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                public void onPageFinished(WebView view, String url) {

                    Log.e("Web","url:" + url);
                  //  Uri uri;
                  //  String paymentToken;
                   // setResult(RESULT_OK);
                  //  finish();
                    if (url.contains("&status")) {
                        if(url.contains("status=success")) {
                            setResult(11);

                            finish();
                        }
                        else{

                            setResult(22);

                            finish();
                        }
                      //  uri = Uri.parse(url);
                      //  paymentToken = uri.getQueryParameter("cko-payment-token");
                      //  PaymentForm.this.m3DSecureListener.onSuccess(paymentToken);
                    }

                }
            });
        }
        else{
            setResult(33);
        }


    }

}
