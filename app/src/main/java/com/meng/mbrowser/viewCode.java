package com.meng.mbrowser;
import android.app.*;
import android.os.*;
import com.meng.mbrowser.tools.*;
import java.io.*;
import java.net.*;
import android.widget.*;

public class viewCode extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_code);
			readCode.start();
	}
	Handler h=new Handler(){
		@Override
        public void handleMessage(Message msg) {
            ((TextView)findViewById(R.id.view_codeTextView)).setText(msg.obj.toString());
        }
	};
	Thread readCode=new Thread(){

		@Override
		public void run(){
			// TODO: Implement this method
			Message m=new Message();
			m.obj=readCode(getIntent().getStringExtra("url"));
			h.sendMessage(m);
		}
		
	};
	public String readCode(String url){
		try{
		HttpURLConnection connection = null;
		URL u = new URL(url);
		connection = (HttpURLConnection) u.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);
		connection.setRequestProperty("cookie", MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.cookieValue));
		connection.setRequestProperty("User-Agent", getIntent().getStringExtra("ua"));
		InputStream in = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		 return sb.toString();	
	
	}catch(Exception e){
		return e.toString();
	}
	}
}
