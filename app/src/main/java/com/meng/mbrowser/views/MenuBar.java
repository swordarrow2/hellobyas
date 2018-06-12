package com.meng.mbrowser.views;

import android.content.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.tools.*;

public class MenuBar extends LinearLayout{

    public Button t1, t2, t3, t4, t5, t6, t7, t8;
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
			R.id.menu_barButton6_addToCollection,
			R.id.menu_barButton7_viewCode,
			R.id.menu_barButton1_exit
        };
        tbs=new Button[]{
			t1, t2, t3, t4, t5, t6, t7, t8
        };
        for(int i = 0; i<8; i++){
            tbs[i]=(Button) findViewById(i1[i]);
            tbs[i].setOnClickListener(onClickListener);
        }
		tbs[1].setOnLongClickListener(new View.OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1){
					// TODO: Implement this method
					//MainActivity.instence.historyTool.clean();
					tool.showToast(getContext(),tool.readTextFile("/data/data/com.meng.mbrowser/history.xml","historys"));
				//	tool.showToast(getContext(),"history cleaned");
					return true;
				}
			});
		tbs[2].setOnLongClickListener(new View.OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1){
					// TODO: Implement this method
					Intent i = new Intent(MainActivity.instence,ShowQrCodeActivity.class);
                    i.putExtra("url",MainActivity.instence.topBar.getUrl());
                    MainActivity.instence.startActivity(i);
					return true;
				}
			});
		tbs[3].setOnLongClickListener(new View.OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1){
					// TODO: Implement this method
					//MainActivity.instence.historyTool.clean();
					tool.showToast(getContext(),tool.readTextFile("/data/data/com.meng.mbrowser/collection.xml","collections"));
				//	tool.showToast(getContext(),"history cleaned");
					return true;
				}
			});
			
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
                    c.startActivity(new Intent(c,PreferenceActivity.class));
                    break;
                case R.id.menu_barButton3_test:
					MainActivity.instence.startActivityForResult(new Intent(c,CaptureActivity.class),55);
					//  c.startActivity(new Intent(c,CaptureActivity.class));
                    break;
                case R.id.menu_barButton4_localhost:
                    webview.loadUrl("http://127.0.0.1:46834/index.html");
                    break;
                case R.id.menu_barButton5_history:
                    Intent i = new Intent(c,HistoryAndCollectionView.class);
                    i.putExtra("isHistory",true);
                    MainActivity.instence.startActivityForResult(i,55);
                    break;
                case R.id.menu_barButton6_addToCollection:
                    try{
                        MainActivity.instence.collectionTool.addCollection(MainActivity.instence.topBar.getUrl());
                        tool.showToast(getContext(),"已将"+MainActivity.instence.topBar.getUrl()+"添加到收藏");
                        //	MainActivity.historyTool.addHistory("http://127.0.0.1:46834/index.html");
                    }catch(Exception e){
                        Toast.makeText(c,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.menu_barButton7_viewCode:
                    Intent iViewCode=new Intent(c,ViewCode.class);
					iViewCode.putExtra("url",MainActivity.instence.topBar.getUrl());
					iViewCode.putExtra("ua",MainActivity.instence.webView.getSettings().getUserAgentString());
					c.startActivity(iViewCode);
                    break;
                case R.id.menu_barButton8_collection:
                    Intent i2 = new Intent(c,HistoryAndCollectionView.class);
                    i2.putExtra("isHistory",false);
                    MainActivity.instence.startActivityForResult(i2,55);
                    break;
            }
        }
    };

	@Override
	public void setVisibility(int visibility){
		// TODO: Implement this method
		if(visibility!=getVisibility()){
			if(visibility==GONE){
				Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.right_out);
				startAnimation(animation);
			}else{
				Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.left_in);
				startAnimation(animation);
			}
		}	
		super.setVisibility(visibility);
	}

}
