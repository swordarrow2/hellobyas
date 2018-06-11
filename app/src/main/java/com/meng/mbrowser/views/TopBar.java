package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.tools.*;

/**
 * Created by Administrator on 2018/5/22.
 */

public class TopBar extends LinearLayout{
    Context c;

    private EditText editTextUrl;
    private ImageButton imageButtonGoto;
	private ImageButton imageButtonqr;
    private ProgressBar pb;
	private TextView tv;

    public TopBar(Context c,AttributeSet a){
        super(c,a);
        this.c=c;
        LayoutInflater.from(c).inflate(R.layout.top_bar,this);
        editTextUrl=(EditText) findViewById(R.id.topBar_EditText_url);
        imageButtonGoto=(ImageButton) findViewById(R.id.topBar_ImageButton_goto);
		imageButtonqr=(ImageButton) findViewById(R.id.topBar_ImageButton_qr);
		tv=(TextView) findViewById(R.id.top_barTextView);
        pb=(ProgressBar) findViewById(R.id.top_barProgressBar);
		tv.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1){
					// TODO: Implement this method
					setIsEdit(true);
					editTextUrl.requestFocus();
					MainActivity.instence.menuBar.setVisibility(GONE);
				}
			});
		imageButtonGoto.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1){
					// TODO: Implement this method
					String cookie =MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.cookieValue);
                    if(!cookie.equals("null")){
                        tool.syncCookie(getContext(),MainActivity.instence.topBar.getUrl(),cookie);
                    }
					MainActivity.instence.webView.loadUrl(getUrl());
					MainActivity.instence.topBar.setIsEdit(false);
				}
			});
		imageButtonGoto.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Intent i = new Intent(MainActivity.instence,ShowQrCodeActivity.class);
				i.putExtra("url",MainActivity.instence.topBar.getUrl());
				MainActivity.instence.startActivity(i);
				return false;
			}
		});
		imageButtonqr.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1){
					// TODO: Implement this method
					MainActivity.instence.startActivityForResult(new Intent(MainActivity.instence,CaptureActivity.class),55);
				}
			});
    }

    public String getUrl(){
        return editTextUrl.getText().toString();
    }

    public void setUrl(String url){
        editTextUrl.setText(url);
		tv.setText(url);
    }

    public void setProgress(int progress){
        if(progress==100){
            pb.setVisibility(GONE);	
        }else{
            pb.setVisibility(VISIBLE);
            pb.setProgress(progress);
        }
    }
	public void setIsEdit(boolean isEdit){
		if(isEdit!=isEdit()){
			if(isEdit){
				tv.setVisibility(GONE);
				editTextUrl.setVisibility(VISIBLE);
				imageButtonGoto.setVisibility(VISIBLE);
				imageButtonqr.setVisibility(VISIBLE);
			}else{
				tv.setVisibility(VISIBLE);
				editTextUrl.setVisibility(GONE);
				imageButtonGoto.setVisibility(GONE);
				imageButtonqr.setVisibility(GONE);
			}
		}
	}
	public boolean isEdit(){
		return editTextUrl.getVisibility()==VISIBLE;
	}
}
