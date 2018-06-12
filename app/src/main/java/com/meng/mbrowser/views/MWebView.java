package com.meng.mbrowser.views;

import android.content.*;
import android.net.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.listener.*;
import com.meng.mbrowser.tools.*;

/**
 * Created by Administrator on 2018/6/12.
 */

public class MWebView extends WebView{
    public MWebView(Context context){
        super(context);
    }
    public MWebView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
    }

	public void init(){
		WebSettings settings=getSettings();
		settings.setJavaScriptEnabled(MainActivity.instence.sharedPreference.getBoolean(Data.preferenceKey.useJavaScript,true));
		settings.setUserAgentString(getUA());
        settings.setCacheMode(Integer.parseInt(MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.cacheMode,"0")));
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadWithOverviewMode(true);
        settings.setGeolocationEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
		setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View p1,MotionEvent p2){
					MainActivity.instence.menuBar.setVisibility(View.GONE);
					MainActivity.instence.topBar.setIsEdit(false);
					return false;
				}
			});
		setDownloadListener(new DownloadListener(){

				@Override
				public void onDownloadStart(String p1,String p2,String p3,String p4,long p5){
					tool.showToast(getContext(),p1+"  "+p2+"   "+p3+"  "+p4+"  "+p5);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setData(Uri.parse(p1));
					MainActivity.instence.startActivity(intent);
				}
			});
		setWebViewClient(new MWebViewClient());
		setWebChromeClient(new MWebChromeClient());
	}

	private String getUA(){
        String data = MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.userAgentList,"default_value");
        if(data.equals("default_value")){
            return getSettings().getUserAgentString();
        }
        if(data.equals("by_user")){
            return MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.userAgent);
        }
        return data;
    }

}
