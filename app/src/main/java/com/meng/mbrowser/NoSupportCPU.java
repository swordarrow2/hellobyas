package com.meng.mbrowser;

import android.app.*;
import android.os.*;
import android.widget.*;

public class NoSupportCPU extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
		//     setTheme(R.style.ErrorTheme);
        setContentView(R.layout.nosupportcpu);
        TextView tv=new TextView(this);
        tv.setText(Build.CPU_ABI);
        setContentView(tv);
    }
}
