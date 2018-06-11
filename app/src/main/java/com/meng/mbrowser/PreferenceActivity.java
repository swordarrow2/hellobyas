package com.meng.mbrowser;

import android.os.*;
import android.preference.*;
import com.meng.mbrowser.tools.*;
import java.io.*;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PreferenceActivity extends android.preference.PreferenceActivity {
	public static PreferenceActivity instence;
    Preference clean;
    ListPreference uaList;
    EditTextPreference uaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		instence=this;
		getPreferenceManager().setSharedPreferencesName(Data.preferenceKey.mainPreferenceName);
		addPreferencesFromResource(R.xml.preference);
		uaList=(ListPreference) findPreference(Data.preferenceKey.userAgentList);
		clean=findPreference(Data.preferenceKey.cleanTmpFilesNow);
		uaInput=(EditTextPreference) findPreference(Data.preferenceKey.userAgent);
		uaInput.setEnabled(uaList.getValue().equals("by_user"));
		uaList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference,Object o){
					uaInput.setEnabled(o.toString().equals("by_user"));
					return true;
				}
			});
		clean.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference){
					File frameFileFolder = new File(Environment.getExternalStorageDirectory().getPath()+File.separator+"tmp");
					deleteFiles(frameFileFolder);
					return true;
				}
			});
	}

    private void deleteFiles(File folder){
        File[] fs=folder.listFiles();
        for(File f:fs){
            if(f.isDirectory()){
                deleteFiles(f);
                f.delete();
            }else{
                f.delete();
            }
        }
    }
}
