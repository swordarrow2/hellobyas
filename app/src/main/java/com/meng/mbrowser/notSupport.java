package com.meng.mbrowser;

import android.app.*;
import android.os.*;
import android.widget.*;

import com.meng.mbrowser.tools.ExceptionCatcher;

public class notSupport extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_support);
        TextView tv=new TextView(this);
        tv.setText("不支持此设备");
        setContentView(tv);
    }
}
