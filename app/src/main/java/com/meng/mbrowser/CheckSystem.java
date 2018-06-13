package com.meng.mbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.meng.mbrowser.tools.ExceptionCatcher;
import com.meng.mbrowser.tools.tool;


public class CheckSystem extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        ExceptionCatcher.getInstance().init(this);
        if (getIntent().getData() != null) {
            String url = getIntent().getData().toString();
            tool.showToast(this, url);
            Intent i = new Intent(this, MainActivity.class);
            i.setData(getIntent().getData());
            startActivity(i);
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
