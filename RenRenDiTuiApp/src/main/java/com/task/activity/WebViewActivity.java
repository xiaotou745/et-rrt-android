package com.task.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.renrentui.app.R;
import com.renrentui.interfaces.INodata;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.util.NetworkHelper;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;

import base.BaseActivity;

/**
 * Created by Administrator on 2015/12/1 0001.
 * webview  信息展示
 */
public class WebViewActivity extends BaseActivity implements
        View.OnClickListener, INodata {
    public static final String STR_CONTENT_URL = "STR_CONTENT_URL";
    public static final String STR_TITLE = "STR_TITLE";

    private WebView mWebView;
    private WebSettings mWebSettings;
    private String strTitle;
    private String strContentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        super.init();
        getIntentData();
       // initTitle();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!NetworkHelper.isNetworkConnected(context)){
            onNodata(ResultMsgType.NetworkNotValide,"","",this);
        }else {
            loadWebViewInfo();
        }
    }

//    private void initTitle() {
//        layout_back.setOnClickListener(this);
//
//    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webview_help);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

    }

    private void getIntentData() {
        strTitle = this.getIntent().getStringExtra(STR_TITLE);
        strContentUrl = this.getIntent().getStringExtra(STR_CONTENT_URL);
        mTV_title_content.setText(strTitle);
    }

    private void loadWebViewInfo() {
        showProgressDialog();
        mWebView.setVisibility(View.INVISIBLE);
        mWebView.loadUrl(strContentUrl);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 加载100
                    hideProgressDialog();
                    mWebView.setVisibility(View.VISIBLE);
                }
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url);
                }
                return true;
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();// 返回上一页面
                } else {
                    WebViewActivity.this.finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();// 返回上一页面
                    return true;
                } else {
                  WebViewActivity.this.finish();
                }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNoData() {
        loadWebViewInfo();
    }
}
