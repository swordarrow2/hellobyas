package com.meng.mbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.meng.mbrowser.tools.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewCode extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_code);
        readCode.start();
    }

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ((TextView) findViewById(R.id.view_codeTextView)).setText(msg.obj.toString());
        }
    };
    Thread readCode = new Thread() {

        @Override
        public void run() {
            // TODO: Implement this method
            Message m = new Message();
            m.obj = readCode(getIntent().getStringExtra("url"));
            h.sendMessage(m);
        }

    };

    public String readCode(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
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

        } catch (Exception e) {
            return e.toString();
        }
    }
}
