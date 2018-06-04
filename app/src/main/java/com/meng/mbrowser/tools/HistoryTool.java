package com.meng.mbrowser.tools;

import android.content.*;
import android.util.Log;
import android.widget.*;
import com.meng.mbrowser.tools.*;
import java.io.*;
import org.apache.http.util.*;

public class HistoryTool {
	Context context;
	private String historyFilePath="/data/data/com.meng.mbrowser/history.xml";
	String historyText="";
	xmlParser xmlparser;
	public HistoryTool(Context c) throws IOException{
		context=c;
		historyText=tool.readTextFile(historyFilePath,"historys");
		xmlparser=new xmlParser(historyFilePath,"history");
	}
	public void addHistory(String s) throws Exception{
		tool.saveTextFile(historyFilePath,historyText=tool.insertText(tool.readTextFile(historyFilePath,"historys"),"\n <history value=\""+s+"\" />","<historys>"));
	}
	public String[] getHistory(){
		try{
			return xmlparser.parseXml();
		}catch(Exception e){
			Log.e(getClass().getName(),e.toString());
			return new String[]{"读取出错",e.toString()};
		}
	}
	/*public void deletehistory(int i){
	 int line=0;
	 int flag=historyText.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<historys>\n");

	 int index=historyText.indexOf("<history",flag);
	 int end=historyText.indexOf("/>",flag)+2;
	 if(i==line){
	 historyText.
	 }

	 }*/
	public void clean(){
		historyText="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<historys>\n</historys>";
		tool.saveTextFile(historyFilePath,historyText);
	}




}
