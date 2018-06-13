package com.meng.mbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.meng.mbrowser.tools.Data;
import com.meng.mbrowser.tools.HistoryAndCollectionTool;
import com.meng.mbrowser.tools.SharedPreferenceHelper;
import com.meng.mbrowser.views.BottomBar;
import com.meng.mbrowser.views.MWebView;
import com.meng.mbrowser.views.MenuBar;
import com.meng.mbrowser.views.TopBar;

public class MainActivity extends Activity {
    public static MainActivity instence;
    public TopBar topBar;
    public MenuBar menuBar;
    public BottomBar bottomBar;
    public SharedPreferenceHelper sharedPreference;
    public MWebView webView;
    public HistoryAndCollectionTool historyAndCollectionTool;
    long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initBasic();
        initPreference();
        initViews();
        initData();
    }

    private void initBasic() {
        instence = this;
        historyAndCollectionTool = new HistoryAndCollectionTool(this);
    }

    private void initPreference() {
        sharedPreference = new SharedPreferenceHelper(this, Data.preferenceKey.mainPreferenceName);
        if (sharedPreference.getValue(Data.preferenceKey.cookieValue) == null) {
            sharedPreference.putValue(Data.preferenceKey.cookieValue, "null");
        }
        if (sharedPreference.getValue(Data.preferenceKey.mainPage) == null || sharedPreference.getValue(Data.preferenceKey.mainPage).equals("")) {
            sharedPreference.putValue(Data.preferenceKey.mainPage, "http://swordarrow2.github.io");
        }
    }

    private void initViews() {
        topBar = (TopBar) findViewById(R.id.topBar);
        menuBar = (MenuBar) findViewById(R.id.menuBar);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        webView = (MWebView) findViewById(R.id.main_webView);
        webView.init();
    }

    private void initData() {
        //	topBar.setUrl(sharedPreference.getValue(Data.preferenceKey.mainPage));
        topBar.setUrl("https://github.com/cn-s3bit/TH902");
        menuBar.setRelationWebView(webView);
        String s = "Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36";
        if (getIntent().getData() != null) {
            webView.loadUrl(getIntent().getData().toString());
        } else {
            webView.loadUrl(topBar.getUrl());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!webView.goToBack()) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 55) {
            webView.loadUrl(data.getStringExtra("url"));
        }
    }

    @Override
    protected void onPause() {
        // TODO: Implement this method
        menuBar.setVisibility(View.GONE);
        super.onPause();
    }

}
