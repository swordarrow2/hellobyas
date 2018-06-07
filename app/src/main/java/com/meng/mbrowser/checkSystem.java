package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;

import com.meng.mbrowser.tools.ExceptionCatcher;


public class checkSystem extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		ExceptionCatcher.getInstance().init(this);
		//	if((((Build.CPU_ABI).indexOf("arm"))!=-1)&(Build.CPU_ABI).indexOf("x86")==-1){
		//		startActivity(new Intent(this,MainActivity.class));
		//	}else{
		//		startActivity(new Intent(this,NoSupportCPU.class));
		//	}
		//	Toast.makeText(this, Build.CPU_ABI, Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
}
