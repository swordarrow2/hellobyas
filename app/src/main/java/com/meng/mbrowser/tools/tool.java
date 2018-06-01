package com.meng.mbrowser.tools;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2018/5/23.
 */

public class tool {
    private final static String ENCODE = "gb2312";
    public static int getAndroidSdkVersion() {
        return Build.VERSION.SDK_INT;
    }
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void syncCookie(Context context,String url,String cookieValue) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookieValue);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
}
