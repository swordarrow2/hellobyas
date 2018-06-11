package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.webkit.*;
import android.widget.*;
import com.meng.mbrowser.listener.*;
import com.meng.mbrowser.tools.*;
import com.meng.mbrowser.views.*;
import java.io.*;

public class MainActivity extends Activity{
	public static MainActivity instence;
    public TopBar topBar;
    public MenuBar menuBar;
    public BottomBar bottomBar;
    public SharedPreferenceHelper sharedPreference;
    public MWebView webView;
	public HistoryTool historyTool;
	public CollectionTool collectionTool;
    long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
		instence=this;
    }

    private void init(){
        sharedPreference=new SharedPreferenceHelper(this,Data.preferenceKey.mainPreferenceName);
        if(sharedPreference.getValue(Data.preferenceKey.cookieValue)==null){
            sharedPreference.putValue(Data.preferenceKey.cookieValue,"null");
        }
        if(sharedPreference.getValue(Data.preferenceKey.mainPage)==null||sharedPreference.getValue(Data.preferenceKey.mainPage).equals("")){
            sharedPreference.putValue(Data.preferenceKey.mainPage,"http://swordarrow2.github.io");
        }
		try{
			historyTool=new HistoryTool(this);
			collectionTool=new CollectionTool(this);
		}catch(IOException e){}	
        topBar=(TopBar) findViewById(R.id.topBar);
        menuBar=(MenuBar) findViewById(R.id.menuBar);
        bottomBar=(BottomBar) findViewById(R.id.bottomBar);
        topBar.setUrl("https://github.com/cn-s3bit/TH902");
		//	topBar.setUrl(sharedPreference.getValue(Data.preferenceKey.mainPage));
        webView=(MWebView) findViewById(R.id.main_webView);
        bottomBar.setOnClickListener(onClickListener);
        menuBar.setRelationWebView(webView);
        webView.getSettings().setJavaScriptEnabled(sharedPreference.getBoolean(Data.preferenceKey.useJavaScript,true));
        String s = "Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv)"+
			" AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36";
        webView.getSettings().setUserAgentString(getUA());
        webView.getSettings().setCacheMode(Integer.parseInt(sharedPreference.getValue(Data.preferenceKey.cacheMode,"0")));
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

		webView.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View p1,MotionEvent p2){
					menuBar.setVisibility(View.GONE);
					topBar.setIsEdit(false);
					return false;
				}
			});
		webView.setDownloadListener(new DownloadListener(){

				@Override
				public void onDownloadStart(String p1,String p2,String p3,String p4,long p5){
					showToast(p1+"  "+p2+"   "+p3+"  "+p4+"  "+p5);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setData(Uri.parse(p1));
					startActivity(intent);
				}
			});
        webView.setWebViewClient(new MWebViewClient());
        webView.setWebChromeClient(new MWebChromeClient());
		if(getIntent().getData()!=null){
			webView.loadUrl(getIntent().getData().toString());
		}else{
			webView.loadUrl(topBar.getUrl());
		}
        //   webView.loadUrl("file:///android_asset/javascript.html");	
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
			topBar.setIsEdit(false);
            switch(v.getId()){

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

	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		if(resultCode==RESULT_OK&&requestCode==55){
			webView.loadUrl(data.getStringExtra("url"));
		}
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
	protected void onPause(){
		// TODO: Implement this method
		menuBar.setVisibility(View.GONE);
		super.onPause();
	}

}
