package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.widget.*;
import android.view.View.*;

/**
 * Created by xdj on 16/9/17.
 */

public class CaptureActivity extends io.github.xudaojie.qrcodelib.CaptureActivity{


    private AlertDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleResult(final String resultString){
        if(TextUtils.isEmpty(resultString)){
            Toast.makeText(this,io.github.xudaojie.qrcodelib.R.string.scan_failed,Toast.LENGTH_SHORT).show();
            restartPreview();
        }else{	
            if(mDialog==null){
                mDialog=new AlertDialog.Builder(this)
					.setMessage(resultString)
					.setNegativeButton("确定",null)
					.setNeutralButton("复制文本",new AlertDialog.OnClickListener(){

						@Override
						public void onClick(DialogInterface p1,int p2){
							// TODO: Implement this method
							android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
							ClipData clipData = ClipData.newPlainText("text",resultString);
							clipboardManager.setPrimaryClip(clipData);
						}
					})
					.setPositiveButton("打开网址",new AlertDialog.OnClickListener(){

						@Override
						public void onClick(DialogInterface p1,int p2){
							// TODO: Implement this method
							Intent i = new Intent();
							i.putExtra("url",resultString);
							setResult(RESULT_OK,i);
							finish();		
						}
					})
					.create();
                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog){
							restartPreview();
						}
					});
            }
            if(!mDialog.isShowing()){
                mDialog.setMessage(resultString);
                mDialog.show();
            }
        }
    }
}
