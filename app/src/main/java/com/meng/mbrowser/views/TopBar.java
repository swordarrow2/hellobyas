package com.meng.mbrowser.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meng.mbrowser.CaptureActivity;
import com.meng.mbrowser.MainActivity;
import com.meng.mbrowser.R;
import com.meng.mbrowser.ShowQrCodeActivity;
import com.meng.mbrowser.tools.Data;
import com.meng.mbrowser.tools.tool;

/**
 * Created by Administrator on 2018/5/22.
 */

public class TopBar extends LinearLayout {
    Context c;

    private EditText editTextUrl;
    private ImageButton imageButtonGoto;
    private ImageButton imageButtonQR;
    private ProgressBar pb;
    private TextView tv;

    public TopBar(Context c, AttributeSet a) {
        super(c, a);
        this.c = c;
        LayoutInflater.from(c).inflate(R.layout.top_bar, this);
        editTextUrl = (EditText) findViewById(R.id.topBar_EditText_url);
        imageButtonGoto = (ImageButton) findViewById(R.id.topBar_ImageButton_goto);
        imageButtonQR = (ImageButton) findViewById(R.id.topBar_ImageButton_QR);
        tv = (TextView) findViewById(R.id.top_barTextView);
        pb = (ProgressBar) findViewById(R.id.top_barProgressBar);
        tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                setIsEdit(true);
                editTextUrl.requestFocus();
                MainActivity.instence.menuBar.setVisibility(GONE);
            }
        });
        imageButtonGoto.setOnClickListener(onClickListener);
        imageButtonQR.setOnClickListener(onClickListener);
        imageButtonQR.setOnLongClickListener(onLongClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topBar_ImageButton_goto:
                    String cookie = MainActivity.instence.sharedPreference.getValue(Data.preferenceKey.cookieValue);
                    if (!cookie.equals("null")) {
                        tool.syncCookie(getContext(), MainActivity.instence.topBar.getUrl(), cookie);
                    }
                    MainActivity.instence.webView.loadUrl(getUrl());
                    MainActivity.instence.topBar.setIsEdit(false);
                    break;
                case R.id.topBar_ImageButton_QR:
                    MainActivity.instence.startActivityForResult(new Intent(MainActivity.instence, CaptureActivity.class), 55);
                    break;
            }
        }
    };
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            switch (view.getId()) {
                case R.id.topBar_ImageButton_QR:
                    Intent i = new Intent(MainActivity.instence, ShowQrCodeActivity.class);
                    i.putExtra("url", MainActivity.instence.topBar.getUrl());
                    MainActivity.instence.startActivity(i);
                    break;
            }
            return true;
        }
    };

    public String getUrl() {
        return editTextUrl.getText().toString();
    }

    public void setUrl(String url) {
        editTextUrl.setText(url);
        tv.setText(url);
    }

    public void setProgress(int progress) {
        if (progress == 100) {
            pb.setVisibility(GONE);
        } else {
            pb.setVisibility(VISIBLE);
            pb.setProgress(progress);
        }
    }

    public void setIsEdit(boolean isEdit) {
        if (isEdit != isEdit()) {
            if (isEdit) {
                tv.setVisibility(GONE);
                editTextUrl.setVisibility(VISIBLE);
                imageButtonGoto.setVisibility(VISIBLE);
                imageButtonQR.setVisibility(VISIBLE);
            } else {
                tv.setVisibility(VISIBLE);
                editTextUrl.setVisibility(GONE);
                imageButtonGoto.setVisibility(GONE);
                imageButtonQR.setVisibility(GONE);
            }
        }
    }

    public boolean isEdit() {
        return editTextUrl.getVisibility() == VISIBLE;
    }
}
