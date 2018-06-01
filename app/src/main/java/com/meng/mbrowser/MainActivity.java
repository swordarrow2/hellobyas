package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import com.meng.mbrowser.history.*;
import com.meng.mbrowser.listener.*;
import com.meng.mbrowser.tools.*;
import com.meng.mbrowser.views.*;
import com.meng.mbrowser.collection.*;
import java.io.*;

public class MainActivity extends Activity{
	public static MainActivity instence;
    public TopBar topBar;
    public MenuBar menuBar;
    public BottomBar bottomBar;
    public sharedPreferenceHelper sharedPreference;
    public WebView webView;
	public historyTool ht;
	public collectionTool ct;
    long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
		instence=this;
    }

    private void init(){
        sharedPreference=new sharedPreferenceHelper(this,Data.preferenceKey.mainPreferenceName);
        if(sharedPreference.getValue(Data.preferenceKey.cookieValue)==null){
            sharedPreference.putValue(Data.preferenceKey.cookieValue,"null");
        }
        if(sharedPreference.getValue(Data.preferenceKey.mainPage)==null||sharedPreference.getValue(Data.preferenceKey.mainPage).equals("")){
            sharedPreference.putValue(Data.preferenceKey.mainPage,"http://swordarrow2.github.io");
        }
		try{
			ht=new historyTool(this);
			ct=new collectionTool(this);
		}catch(IOException e){}	
        topBar=(TopBar) findViewById(R.id.topBar);
        menuBar=(MenuBar) findViewById(R.id.menuBar);
        bottomBar=(BottomBar) findViewById(R.id.bottomBar);
        topBar.setUrl("https://github.com/cn-s3bit/TH902");
	//	topBar.setUrl(sharedPreference.getValue(Data.preferenceKey.mainPage));
        webView=(WebView) findViewById(R.id.main_webView);
        topBar.setOnClickListener(onClickListener);
        bottomBar.setOnClickListener(onClickListener);
        menuBar.setRelationWebView(webView);
        webView.getSettings().setJavaScriptEnabled(sharedPreference.getBoolean(Data.preferenceKey.useJavaScript,true));
        String s = "Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv)"+
			" AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36";
        webView.getSettings().setUserAgentString(getUA());
        webView.getSettings().setCacheMode(Integer.parseInt(sharedPreference.getValue(Data.preferenceKey.cacheMode,"0")));

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(true);
		webView.setDownloadListener(new DownloadListener(){

				@Override
				public void onDownloadStart(String p1,String p2,String p3,String p4,long p5){
					// TODO: Implement this method
					showToast(p1+"  "+p2+"   "+p3+"  "+p4+"  "+p5);


					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setData(Uri.parse(p1));
					startActivity(intent);

				}
			});
        webView.setWebViewClient(new MWebViewClient());


        webView.setWebChromeClient(new MWebChromeClient());
        //   webView.loadUrl("file:///android_asset/javascript.html");	
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.topBar_ImageButton_goto:
                    String cookie = sharedPreference.getValue(Data.preferenceKey.cookieValue);
                    if(!cookie.equals("null")){
                        tool.syncCookie(getApplicationContext(),topBar.getUrl(),cookie);
                    }
                    webView.loadUrl(topBar.getUrl());
                    break;
                case R.id.bottomBar_ImageButton_back:
                    goBack();
                    break;
                case R.id.bottomBar_ImageButton_forward:
                    goForward();
                    break;
                case R.id.bottomBar_ImageButton_home:
                    topBar.setUrl(sharedPreference.getValue(Data.preferenceKey.mainPage));
                    webView.loadUrl(sharedPreference.getValue(Data.preferenceKey.mainPage));
                    break;
                case R.id.bottomBar_ImageButton_menu:
                    if(menuBar.getVisibility()==View.GONE){
                        menuBar.setVisibility(View.VISIBLE);
                    }else{
                        menuBar.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private boolean goBack(){
        if(webView.canGoBack()){
            webView.goBack();
        }
        return webView.canGoBack();
    }

    private boolean goForward(){
        if(webView.canGoForward()){
            webView.goForward();
        }
        return webView.canGoForward();
    }

    public void showToast(Object o){
        Toast.makeText(this,o.toString(),Toast.LENGTH_SHORT).show();
    }

	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		// TODO: Implement this method
		if(keyCode==KeyEvent.KEYCODE_BACK){
            if(!goBack()){
                if((System.currentTimeMillis()-exitTime)>2000){
                    Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    exitTime=System.currentTimeMillis();
                }else{
                    finish();
                }
            }
        }
		return true;
	}



    private String getUA(){
        String data = sharedPreference.getValue(Data.preferenceKey.userAgentList,"default_value");
        if(data.equals("default_value")){
            return webView.getSettings().getUserAgentString();
        }
        if(data.equals("by_user")){
            return sharedPreference.getValue(Data.preferenceKey.userAgent);
        }
        return data;
    }

	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		// TODO: Implement this method
		if(resultCode==RESULT_OK&&requestCode==55){
			webView.loadUrl(data.getStringExtra("url"));
		}

	}

}
