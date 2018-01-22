package com.template_c.app.features;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.template_c.app.MainActivity;
import com.template_c.app.R;

/**
 * Created by Djaffar on 7/10/2017.
 */

public class Pulsa extends AppCompatActivity {
    // local variables
    WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_webview);

        // state custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // disable toobar default title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // swipe refresh method
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWeb();
            }
        });

        // load the web
        LoadWeb();

        // call home button function
        HomeBtn();
    }

    public void LoadWeb() {
        // state the webview
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        // set the webview zoom to farest possible. why? to counter unresponsive website
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // allow webview to be refreshed
        swipeRefreshLayout.setRefreshing(true);

        // enable built-in zoom function
        webView.getSettings().setBuiltInZoomControls(true);

        // url to load
        webView.loadUrl("http://transaksi.klikmbc.co.id/pulsa");

        // webview behaviour
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                webView.loadUrl("file:///android_asset/error.html");
            }

            public void onPageFinished(WebView view, String url) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void HomeBtn() {
        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pulsa.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // set device back button act like browser
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
