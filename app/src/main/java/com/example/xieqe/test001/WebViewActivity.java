package com.example.xieqe.test001;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieqe on 2018/4/2.
 */

public class WebViewActivity extends Activity {

    private static final String TAG = "WebViewActivity";
    WebView webview;
    WeakReference<Context> contextWeakReference;
    @BindView(R.id.webview_container)
    FrameLayout webviewContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initWebView();
    }

    @SuppressLint("setJavaScriptEnabled")
    private void initWebView() {
        contextWeakReference = new WeakReference<Context>(this);
        //动态加载webView + 弱引用 + 退出Activity时移除，防止内存泄漏
        webview = new WebView(contextWeakReference.get());
        webviewContainer.addView(webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.loadUrl("http://www.hst.com/");

        webview.setWebChromeClient(new WebChromeClient() {

            // 由于设置了弹窗检验调用结果,所以需要支持js对话框
            // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
            // 通过设置WebChromeClient对象处理JavaScript的对话框
            //设置响应js 的Alert()函数
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                Log.i(TAG, "onJsAlert: ");
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }


            //当webView需要加载各种各样的网页，且需要在网页上完成一些操作时，
            //建议使用WebChromeClient.onProgressChanged进行page页面回调，
            //progress 0-100，100时加载完成，可做相应操作
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i(TAG, "onProgressChanged: " + newProgress);
            }
        });

        webview.setWebViewClient(new WebViewClient(){
            //webViewClient.onPageFinished表示页面加载完成
            //但如果当前正在加载的网页产生跳转，该方法会被调用无数次
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished: ");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除webView 并调用webView.destroy防止内存泄漏
        webview.removeAllViews();
        webviewContainer.removeView(webview);
        webview.destroy();
    }
}
