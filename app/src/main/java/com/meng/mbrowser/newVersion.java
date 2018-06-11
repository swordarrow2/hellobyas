package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.view.*;
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
        StringBuilder sb = new StringBuilder();
        String[] s = {
				getString(R.string.update_content_text_2_6),
				getString(R.string.update_content_text_2_5_1),
			getString(R.string.update_content_text_2_5),
			getString(R.string.update_content_text_2_4),
			getString(R.string.update_content_text_2_3),
			getString(R.string.update_content_text_2_2_1),
			getString(R.string.update_content_text_2_2),
			getString(R.string.update_content_text_2_1),
			getString(R.string.update_content_text_2_0),
			getString(R.string.update_content_text_1_9_1),
			getString(R.string.update_content_text_1_9),
			getString(R.string.update_content_text_1_8),
			getString(R.string.update_content_text_1_7),
			getString(R.string.update_content_text_1_6),
			getString(R.string.update_content_text_1_5),
			getString(R.string.update_content_text_1_4),
			getString(R.string.update_content_text_1_3),
			getString(R.string.update_content_text_1_2),
			getString(R.string.update_content_text_1_1)
        };
        for(String tmp : s){
            sb.append(tmp);
        }
        tv.setText(sb);
		CharSequence text = tv.getText();  
		if(text instanceof Spannable){  
			int end = text.length();  
			Spannable sp = (Spannable) text;  
			URLSpan urls[] = sp.getSpans(0,end,URLSpan.class);  
			SpannableStringBuilder style = new SpannableStringBuilder(text);  
			style.clearSpans();  
			for(URLSpan urlSpan : urls){  
				MyURLSpan myURLSpan = new MyURLSpan(urlSpan.getURL());  
				style.setSpan(myURLSpan,sp.getSpanStart(urlSpan),  
							  sp.getSpanEnd(urlSpan),  
							  Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}  
			tv.setText(style);  
		}  
    }

	private class MyURLSpan extends ClickableSpan{  

		private String url;  
		public MyURLSpan(String url){  
			this.url=url;  
		}  

		@Override  
		public void onClick(View arg0){
			MainActivity.instence.webView.loadUrl(url);
			MainActivity.instence.topBar.setUrl(url);
			MainActivity.instence.historyTool.addHistory(url);
			preferenceActivity.instence.finish();
			finish();
		}  

	}  


}
