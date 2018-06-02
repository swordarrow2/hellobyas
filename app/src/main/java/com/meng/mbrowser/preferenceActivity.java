package com.meng.mbrowser;

import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.meng.mbrowser.tools.Data;

import java.io.File;

/**
 * Created by Administrator on 2018/3/13.
 */

public class preferenceActivity extends PreferenceActivity{
    Preference clean;
    ListPreference uaList;
    EditTextPreference uaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
