package com.tekinarslan.material.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import junit.framework.Test;

/**
 * Created by kaishen on 16/6/15.
 */
public class MovieWebActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        initView();
        initData();
        mWebView.setWebViewClient(new WebViewClient() {
            //用于加载新WebView,返回true代表着用完就消费掉
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            //用于加载新Webview之前，一般在此加载缓冲区
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                showDialog(1);
            }
            //用于加载新Webview之后，一般在此消除缓冲区
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                removeDialog(1);
            }
            //加载错误时调用，一般提示错误信息
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Toast.makeText(MovieWebActivity.this, "页面加载错误...",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("LoadUrl");
        Log.e("get",mUrl);
        setSupportActionBar(mToolbar);
        //mToolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);


    }

    public void initView() {

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mWebView = (WebView)findViewById(R.id.wv_movieinfo);
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && !(mWebView.canGoBack())) {
            Intent intent = new Intent();
            intent.setClass(this,SampleActivity.class);
            startActivity(intent);
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
