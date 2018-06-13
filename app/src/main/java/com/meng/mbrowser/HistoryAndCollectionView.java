package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class HistoryAndCollectionView extends Activity{
    ListView list;
    ArrayAdapter<String> adapter;
	Button btnClean;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_and_collection);
		btnClean=(Button)findViewById(R.id.history_and_collectionButton);
        list=(ListView) findViewById(R.id.historyAndCollectionListView);
        if(getIntent().getBooleanExtra("isHistory",false)){
            setTitle("历史记录");
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MainActivity.instence.historyTool.getHistory());
            list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> p1,View p2,int p3,long p4){
						// TODO: Implement this method
						String s = p1.getItemAtPosition(p3).toString();
						if(s.equals("清除所有条目")){
							cleanHistory();
						}else{
							returnURL(s);
						}
					}
				});
			btnClean.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1){
						// TODO: Implement this method
						cleanHistory();
					}
				});
        }else{
            setTitle("收藏");
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MainActivity.instence.collectionTool.getCollection());
            list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> p1,View p2,int p3,long p4){
						// TODO: Implement this method
						String s = p1.getItemAtPosition(p3).toString();
						if(s.equals("清除所有条目")){
							cleanCollection();
						}else{
							returnURL(s);
						}
					}
				});
			btnClean.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1){
						// TODO: Implement this method
						cleanCollection();
					}
				});
        }
        list.setAdapter(adapter);


    }

    private void returnURL(String s){
        Intent i = new Intent();
        i.putExtra("url",s);
        setResult(RESULT_OK,i);
        finish();
    }
	private void cleanHistory(){
		MainActivity.instence.historyTool.clean();
		adapter=new ArrayAdapter<String>(HistoryAndCollectionView.this,android.R.layout.simple_list_item_1,new String[]{""});
		list.setAdapter(adapter);
	}
	private void cleanCollection(){
		MainActivity.instence.collectionTool.clean();
		adapter=new ArrayAdapter<String>(HistoryAndCollectionView.this,android.R.layout.simple_list_item_1,new String[]{""});
		list.setAdapter(adapter);
	}

}
