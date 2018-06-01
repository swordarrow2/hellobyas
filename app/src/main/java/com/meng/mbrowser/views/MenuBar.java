package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.history.*;
import com.meng.mbrowser.tools.*;
import java.io.*;
import java.util.*;

public class MenuBar extends LinearLayout{

	public Button t1,t2,t3,t4,t5,t6,t7,t8;
	public Button tbs[];
	public int[] i1;
	WebView webview;
	Context c;
	public MenuBar(Context c,AttributeSet a){
		super(c,a);
		this.c=c;
		LayoutInflater.from(c).inflate(R.layout.menu_bar,this);
		i1=new int[]{
			R.id.menu_barButton4_localhost,
			R.id.menu_barButton5_history,
			R.id.menu_barButton3_test,
			R.id.menu_barButton8_collection,
			R.id.menu_barButton2_settings,
			R.id.menu_barButton6_add,
			R.id.menu_barButton7_delete,
			R.id.menu_barButton1_exit
		};
		tbs=new Button[]{
			t1,t2,t3,t4,t5,t6,t7,t8
		};

		for(int i=0;i<8;i++){
			tbs[i]=(Button) findViewById(i1[i]);
			tbs[i].setOnClickListener(onClickListener);

		}

	}
	public void setRelationWebView(WebView webview){
		this.webview=webview;
	}
	View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.menu_barButton1_exit:     
                    System.exit(0);
                    break;
                case R.id.menu_barButton2_settings:
                    c.startActivity(new Intent(c,preferenceActivity.class));
                    break;
                case R.id.menu_barButton3_test:
                    c.startActivity(new Intent(c,p2.class));
                    break;
				case R.id.menu_barButton4_localhost:
					webview.loadUrl("http://127.0.0.1:46834/index.html");
					break;
				case R.id.menu_barButton5_history:
					MainActivity.instence.startActivityForResult(new Intent(c,historyView.class),55);
					break;
				case R.id.menu_barButton6_add:
					try{
						MainActivity.instence.ct.addCollection(MainActivity.instence.topBar.getUrl());
					//	MainActivity.ht.addHistory("http://127.0.0.1:46834/index.html");
						//MainActivity.ht.addHistory("http://www.baidu.com"+new Random().nextInt());
					}catch(Exception e){
						Toast.makeText(c,e.toString(),Toast.LENGTH_SHORT).show();}
					break;
				case R.id.menu_barButton7_delete:
						MainActivity.instence.ht.cleanHistory();
					break;
					case R.id.menu_barButton8_collection:
					MainActivity.instence.startActivityForResult(new Intent(c,collectionView.class),55);
					break;
            }
        }
    };
}
