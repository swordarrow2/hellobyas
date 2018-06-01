package com.meng.mbrowser.listener;
import android.app.*;
import android.content.*;
import android.webkit.*;
import com.meng.mbrowser.*;

public class MWebChromeClient extends WebChromeClient
{
	@Override
	public boolean onJsAlert(WebView view,String url,String message,final JsResult result){
		AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
		b.setTitle("来自"+MainActivity.webView.getTitle()+"的提示");
		b.setMessage(message);
		b.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which){
					result.confirm();
				}
			});
		b.setCancelable(false);
		b.create().show();
		return true;
	}

	@Override
	public void onProgressChanged(WebView view,int newProgress){
		MainActivity.topBar.setProgress(newProgress);
	}

}
