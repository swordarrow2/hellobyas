package com.meng.mbrowser.tools;

import android.os.*;
import android.preference.*;
import com.meng.mbrowser.*;

/**
 * Created by Administrator on 2018/3/13.
 */

public class p2 extends PreferenceActivity{
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.p2);
    }

}
