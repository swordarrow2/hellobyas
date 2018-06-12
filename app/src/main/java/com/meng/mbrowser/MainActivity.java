package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.meng.mbrowser.tools.*;
import com.meng.mbrowser.views.*;

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
		instence=this;
        init();

    }

    private void init(){
        sharedPreference=new SharedPreferenceHelper(this,Data.preferenceKey.mainPreferenceName);
        if(sharedPreference.getValue(Data.preferenceKey.cookieValue)==null){
            sharedPreference.putValue(Data.preferenceKey.cookieValue,"null");
        }
        if(sharedPreference.getValue(Data.preferenceKey.mainPage)==null||sharedPreference.getValue(Data.preferenceKey.mainPage).equals("")){
            sharedPreference.putValue(Data.preferenceKey.mainPage,"http://swordarrow2.github.io");
        }
		historyTool=new HistoryTool(this);
		collectionTool=new CollectionTool(this);
        topBar=(TopBar) findViewById(R.id.topBar);
        menuBar=(MenuBar) findViewById(R.id.menuBar);
        bottomBar=(BottomBar) findViewById(R.id.bottomBar);
        webView=(MWebView) findViewById(R.id.main_webView);
		//	topBar.setUrl(sharedPreference.getValue(Data.preferenceKey.mainPage));
		topBar.setUrl("https://github.com/cn-s3bit/TH902");
		webView.init();
        bottomBar.setOnClickListener(onClickListener);
        menuBar.setRelationWebView(webView);
		String s = "Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv)"+
			" AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36";
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

	@Override
	protected void onPause(){
		// TODO: Implement this method
		menuBar.setVisibility(View.GONE);
		super.onPause();
	}

}
