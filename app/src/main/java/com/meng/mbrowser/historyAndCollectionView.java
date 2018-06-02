package com.meng.mbrowser;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.view.*;

public class historyAndCollectionView extends Activity {
    TextView text;
    ListView list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_and_collection);
        Intent intent = getIntent();

        text = (TextView) findViewById(R.id.historyAndCollectionTextView);
        list = (ListView) findViewById(R.id.historyAndCollectionListView);
        if (intent.getBooleanExtra("isHistory", false)) {
            text.setText("历史记录");
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.instence.historyTool.getHistory());
        } else {
            text.setText("收藏");
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.instence.collectionTool.getCollection());
        }
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                // TODO: Implement this method
                returnURL(p1.getItemAtPosition(p3).toString());
            }
        });

    }

    private void returnURL(String s) {
        Intent i = new Intent();
        i.putExtra("url", s);
        setResult(RESULT_OK, i);
        finish();

    }

}
