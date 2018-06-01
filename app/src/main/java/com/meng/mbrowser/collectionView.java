package com.meng.mbrowser;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.view.*;

public class collectionView extends Activity{
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		list=(ListView) findViewById(R.id.historyListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MainActivity.instence.ct.getCollection());
        list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1,View p2,int p3,long p4){
					// TODO: Implement this method
					returnURL(p1.getItemAtPosition(p3).toString());
				}
			});

	}
	private void returnURL(String s){
		Intent i=new Intent();
		i.putExtra("url",s);
		setResult(RESULT_OK,i);
		finish();
		
	}
	
}
