package com.meng.mbrowser;

import android.app.*;
import android.os.*;
import android.widget.*;

/**
 * Created by Administrator on 2018/4/20.
 */

public class newVersion extends Activity{
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_version);
        tv=(TextView) findViewById(R.id.new_version_textview);
        StringBuilder sb=new StringBuilder();
        String[] s={
	/*		getString(R.string.update_content_text_1_4_1),
			getString(R.string.update_content_text_1_4),
			getString(R.string.update_content_text_1_3_2),
			getString(R.string.update_content_text_1_3_1),
			getString(R.string.update_content_text_1_3),
			getString(R.string.update_content_text_1_2_1),
			getString(R.string.update_content_text_1_2),*/
			getString(R.string.update_content_text_1_1)
        };
        for(String tmp:s){
            sb.append(tmp);
        }
        tv.setText(sb);
    }
}
