package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.meng.mbrowser.*;

/**
 * Created by Administrator on 2018/5/22.
 */

public class TopBar extends LinearLayout{
    Context c;

    private EditText editTextUrl;
    private ImageButton imageButtonGoto;
	private ProgressBar pb;

    public TopBar(Context c,AttributeSet a){
        super(c,a);
        this.c=c;
        LayoutInflater.from(c).inflate(R.layout.top_bar,this);
        editTextUrl=(EditText)findViewById(R.id.topBar_EditText_url);
        imageButtonGoto=(ImageButton)findViewById(R.id.topBar_ImageButton_goto);
		pb=(ProgressBar) findViewById(R.id.top_barProgressBar);
    }
    public void setOnClickListener(OnClickListener onClickListener){
        imageButtonGoto.setOnClickListener(onClickListener);
    }
    public String getUrl(){
        return editTextUrl.getText().toString();
    }
    public void setUrl(String url){
        editTextUrl.setText(url);
    }
	public void setProgress(int progress){
		if(progress==100){
			pb.setVisibility(GONE);
		}else{
			pb.setVisibility(VISIBLE);
			pb.setProgress(progress);
		}
	}
}
