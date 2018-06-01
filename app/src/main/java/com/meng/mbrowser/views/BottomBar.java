package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.meng.mbrowser.*;

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
    }

    public void setOnClickListener(OnClickListener onClickListener){
        imageButtonBack.setOnClickListener(onClickListener);
        imageButtonForward.setOnClickListener(onClickListener);
        imageButtonHome.setOnClickListener(onClickListener);
        imageButtonMenu.setOnClickListener(onClickListener);
    }
}
