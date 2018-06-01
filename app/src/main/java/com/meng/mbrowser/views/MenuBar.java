package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;

import com.meng.mbrowser.*;
import com.meng.mbrowser.tools.*;
import com.meng.mbrowser.history.*;

import java.io.*;

public class MenuBar extends LinearLayout {
    historyTool ht;
    public Button t1, t2, t3, t4, t5, t6, t7, t8;
    public Button tbs[];
    WebView webview;
    Context c;

    public MenuBar(Context c, AttributeSet a) throws IOException {
        super(c, a);
        this.c = c;
        ht = new historyTool(c);
        LayoutInflater.from(c).inflate(R.layout.menu_bar, this);
        int buttonid = R.id.menu_barButton1_exit;
        tbs = new Button[]{
                t1, t2, t3, t4, t5, t6, t7, t8
        };
        for (int i = 0; i < 8; i++) {
            tbs[i] = (Button) findViewById(buttonid + i);
            tbs[i].setOnClickListener(onClickListener);

        }

    }

    public void setRelationWebView(WebView webview) {
        this.webview = webview;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_barButton1_exit:
                    System.exit(0);
                    break;
                case R.id.menu_barButton2_settings:
                    c.startActivity(new Intent(c, preferenceActivity.class));
                    break;
                case R.id.menu_barButton3_test:
                    c.startActivity(new Intent(c, p2.class));
                    break;
                case R.id.menu_barButton4_localhost:
                    webview.loadUrl("http://127.0.0.1:46834/index.html");
                    break;
                case R.id.menu_barButton5_history:
                    c.startActivity(new Intent(c, historyView.class));
                    break;
                case R.id.menu_barButton6:
                    try {
                        ht.addHistory("http://www.baidu.com");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
