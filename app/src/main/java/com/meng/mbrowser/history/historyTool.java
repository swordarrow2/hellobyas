package com.meng.mbrowser.history;

import android.content.*;
import java.io.*;
import org.apache.http.util.*;
import android.widget.*;

public class historyTool{
	Context context;
	public static final String historyFilePath="/data/data/com.meng.mbrowser/history.xml";
	String historyText="";
	public historyTool(Context c) throws IOException{
		context=c;
		historyText=readTextFile(historyFilePath);
	}
	public void addHistory(String s) throws IOException {
		showToast(historyText=readTextFile(historyFilePath));

		saveTextFile(insertText(historyText,s),historyFilePath);
	}
	public String[] getHistory(){
		return null;
	}
	public void cleanHistory(){

	}

	public static String readTextFile(String textFilePath) throws IOException{
        File f = new File(textFilePath);
		if(!f.exists()){
			f.createNewFile();
			saveTextFile("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<historys>\n</historys>",historyFilePath);
		}
        String res = "";
        
		FileInputStream fin = new FileInputStream(textFilePath);
		int length = fin.available();
		byte[] buffer = new byte[length];
		fin.read(buffer);
		res=EncodingUtils.getString(buffer,"UTF-8");
		fin.close();

        return res;
    }

    public String insertText(String fileText,String textToInsert){
        int index = fileText.indexOf("<historys>")+10;
        StringBuilder sb = new StringBuilder(fileText);
        sb.insert(index,"\n <history value=\""+textToInsert+"\" />");
        return sb.toString();
    }

    public static boolean saveTextFile(String textToSave,String filePath){
        try{
            FileOutputStream fout = new FileOutputStream(filePath);
            byte[] bytes = textToSave.getBytes();
            fout.write(bytes);
            fout.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
	private void showToast(Object o){
		Toast.makeText(context,o.toString(),Toast.LENGTH_SHORT).show();
	}


}
