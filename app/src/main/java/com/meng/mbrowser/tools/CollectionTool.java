package com.meng.mbrowser.tools;

import android.content.*;
import android.util.*;
import java.io.*;

public class CollectionTool{
	Context context;
	private final String collectionFilePath="/data/data/com.meng.mbrowser/collection.xml";
	String collectionText="";
	xmlParser xmlparser;
	public CollectionTool(Context c){
		context=c;
		collectionText=tool.readTextFile(collectionFilePath,"collections");
		xmlparser=new xmlParser(collectionFilePath,"collection");
	}
	public void addCollection(String s){
		try{
			tool.saveTextFile(collectionFilePath,collectionText=tool.insertText(tool.readTextFile(collectionFilePath,"collections"),"\n <collection value=\""+s+"\" />","<collections>"));
		}catch(Exception e){
			tool.showToast(context,e.toString());
		}
	}
	public String[] getCollection(){
		try{
			return xmlparser.parseXml();
		}catch(Exception e){
			Log.e(getClass().getName(),e.toString());
			return new String[]{"读取出错",e.toString()};
		}
	}
	/*public void deletecollection(int i){
	 int line=0;
	 int flag=collectionText.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<collections>\n");

	 int index=collectionText.indexOf("<collection",flag);
	 int end=collectionText.indexOf("/>",flag)+2;
	 if(i==line){
	 collectionText.
	 }

	 }*/
	public void clean(){
		collectionText="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<collections>\n</collections>";
		tool.saveTextFile(collectionFilePath,collectionText);
	}




}
