package com.meng.mbrowser.listener;

import android.os.*;
import android.webkit.*;

import com.meng.mbrowser.*;
import com.meng.mbrowser.tools.*;

import android.graphics.*;

public class MWebViewClient extends WebViewClient {
    String lastUrl = "";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        MainActivity.instence.topBar.setUrl(url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        lastUrl = url;
        MainActivity.instence.topBar.setUrl(MainActivity.instence.webView.getUrl());
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        if (CookieStr != null) {
            MainActivity.instence.sharedPreference.putValue(Data.preferenceKey.cookieValue, CookieStr);
        }
        MainActivity.instence.webView.loadUrl("javascript:callJS()");
        //MainActivity.instence.webView.loadUrl("javascript:document.write(\"hhh\")");
        //    webView.loadUrl("javascript:navigator.__defineGetter__('userAgent', function(){ return 'Mozilla/5.0 (Symbian/3; Series60/5.2 NokiaN8-00/012.002; Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/533.4 (KHTML, like Gecko) NokiaBrowser/7.3.0 Mobile Safari/533.4 3gpp-gba'; });");
        //    webView.loadUrl("javascript:alert(\"jsAlert\");");
        if (tool.getAndroidSdkVersion() < Build.VERSION_CODES.KITKAT) {
            //webView.loadUrl("javascript:alert(\"Android调用了JS的callJS方法\");");
            //  webView.loadUrl("javascript:navigator.__defineGetter__('userAgent', function(){ return 'Mozilla/5.0 (Symbian/3; Series60/5.2 NokiaN8-00/012.002; Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/533.4 (KHTML, like Gecko) NokiaBrowser/7.3.0 Mobile Safari/533.4 3gpp-gba'; });");
        } else {
            //            webView.evaluateJavascript("javascript:alert(\"Android调用了JS的callJS方法\");", new ValueCallback<String>() {
            //           @Override
            //           public void onReceiveValue(String value) {
            //此处为 js 返回的结果
            //           }
            //        });
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // TODO: Implement this method
        if (!lastUrl.equals(url)) {
            try {
                MainActivity.instence.topBar.setUrl(url);
                MainActivity.instence.historyTool.addHistory(url);
            } catch (Exception e) {
                tool.showToast(view.getContext(), e.toString());
            }
        }
        super.onPageStarted(view, url, favicon);
    }

}
