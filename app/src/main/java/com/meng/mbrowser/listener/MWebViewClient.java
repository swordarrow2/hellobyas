package com.meng.mbrowser.listener;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.net.http.SslError;
import android.os.*;
import android.view.KeyEvent;
import android.webkit.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.tools.*;

public class MWebViewClient extends WebViewClient{
    String lastUrl = "";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view,String url){
        MainActivity.instence.topBar.setUrl(url);
        if(url.startsWith("https") || url.startsWith("http")){
            view.loadUrl(url);
        }else{
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                MainActivity.instence.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // TODO: handle exception
            }
        }
        return true;
    }

    @Override
    public void onPageFinished(WebView view,String url){
        super.onPageFinished(view,url);
        lastUrl=url;
        MainActivity.instence.topBar.setUrl(MainActivity.instence.webView.getUrl());
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        if(CookieStr!=null){
            MainActivity.instence.sharedPreference.putValue(Data.preferenceKey.cookieValue,CookieStr);
        }
        //    webView.loadUrl("javascript:navigator.__defineGetter__('userAgent', function(){ return 'Mozilla/5.0 (Symbian/3; Series60/5.2 NokiaN8-00/012.002; Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/533.4 (KHTML, like Gecko) NokiaBrowser/7.3.0 Mobile Safari/533.4 3gpp-gba'; });");
        //    MainActivity.instence.webView.loadUrl("javascript:alert(\"jsAlert\");");
        if(tool.getAndroidSdkVersion()<Build.VERSION_CODES.KITKAT){
            //  webView.loadUrl("javascript:navigator.__defineGetter__('userAgent', function(){ return 'Mozilla/5.0 (Symbian/3; Series60/5.2 NokiaN8-00/012.002; Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/533.4 (KHTML, like Gecko) NokiaBrowser/7.3.0 Mobile Safari/533.4 3gpp-gba'; });");
        }else{
            //            webView.evaluateJavascript("javascript:alert(\"Android调用了JS的callJS方法\");", new ValueCallback<String>() {
            //           @Override
            //           public void onReceiveValue(String value) {
            //此处为 js 返回的结果
            //           }
            //        });
        }
    }

    @Override
    public void onPageStarted(WebView view,String url,Bitmap favicon){
        // TODO: Implement this method
        if(!lastUrl.equals(url)){
            try{
                MainActivity.instence.topBar.setUrl(url);
            }catch(Exception e){
                tool.showToast(view.getContext(),e.toString());
            }
        }
        super.onPageStarted(view,url,favicon);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }
}
