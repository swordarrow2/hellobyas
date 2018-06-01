package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;


public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
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
