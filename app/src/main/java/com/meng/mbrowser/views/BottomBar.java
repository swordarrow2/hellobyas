package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.tools.Data;

/**
 * Created by Administrator on 2018/5/22.
 */

public class BottomBar extends LinearLayout{
    Context c;
    private ImageButton imageButtonBack;
    private ImageButton imageButtonForward;
    private ImageButton imageButtonHome;
    private ImageButton imageButtonMenu;

    public BottomBar(Context c,AttributeSet a){
        super(c,a);
        this.c=c;
        LayoutInflater.from(c).inflate(R.layout.bottom_bar,this);
        imageButtonBack=(ImageButton) findViewById(R.id.bottomBar_ImageButton_back);
        imageButtonForward=(ImageButton) findViewById(R.id.bottomBar_ImageButton_forward);
        imageButtonHome=(ImageButton) findViewById(R.id.bottomBar_ImageButton_home);
        imageButtonMenu=(ImageButton) findViewById(R.id.bottomBar_ImageButton_menu);
        imageButtonBack.setOnClickListener(onClickListener);
        imageButtonForward.setOnClickListener(onClickListener);
        imageButtonHome.setOnClickListener(onClickListener);
        imageButtonMenu.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            MainActivity.instence.topBar.setIsEdit(false);
            switch(v.getId()){
                case R.id.bottomBar_ImageButton_back:
                    MainActivity.instence.webView.goToBack();
                    break;
                case R.id.bottomBar_ImageButton_forward:
                    MainActivity.instence.webView.goToForward();
                    break;
                case R.id.bottomBar_ImageButton_home:
                    MainActivity.instence.topBar.setUrl(MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.mainPage));
                    MainActivity.instence.webView.loadUrl(MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.mainPage));
                    break;
                case R.id.bottomBar_ImageButton_menu:
                    if(MainActivity.instence.menuBar.getVisibility()==View.GONE){
                        MainActivity.instence.menuBar.setVisibility(View.VISIBLE);
                    }else{
                        MainActivity.instence.menuBar.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
}
