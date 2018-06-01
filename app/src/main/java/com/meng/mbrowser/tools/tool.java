package com.meng.mbrowser.tools;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.UnsupportedEncodingException;
import java.io.*;
import org.apache.http.util.*;
import android.widget.*;

/**
 * Created by Administrator on 2018/5/23.
 */

public class tool {
    private final static String ENCODE = "utf-8";
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
	

	public static String readTextFile(String file,String fileTagWhenNoFile) throws IOException{
        File f = new File(file);
		if(!f.exists()){
			f.createNewFile();
			saveTextFile(file,"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<"+fileTagWhenNoFile+">\n</"+fileTagWhenNoFile+">");
		}
        String res = "";
		FileInputStream fin = new FileInputStream(file);
		int length = fin.available();
		byte[] buffer = new byte[length];
		fin.read(buffer);
		res=EncodingUtils.getString(buffer,"UTF-8");
		fin.close();

        return res;
    }

    public static String insertText(String text,String textToInsert,String indexOf)throws Exception{
        int index = text.indexOf(indexOf)+indexOf.length();
        StringBuilder sb = new StringBuilder(text);
        sb.insert(index,textToInsert);
        return sb.toString();
    }
	
	public static boolean saveTextFile(String file,String textToSave){
        try{
            FileOutputStream fout = new FileOutputStream(file);
            byte[] bytes = textToSave.getBytes();
            fout.write(bytes);
            fout.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
	public static void showToast(Context c,Object o){
		Toast.makeText(c,o.toString(),Toast.LENGTH_SHORT).show();
	}
	
	
}
