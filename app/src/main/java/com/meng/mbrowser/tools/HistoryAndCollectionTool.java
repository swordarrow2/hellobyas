package com.meng.mbrowser.tools;

import android.content.Context;
import android.util.Log;

public class HistoryAndCollectionTool {
    Context context;
    private final String collectionFilePath = "/data/data/com.meng.mbrowser/collection.xml";
    private final String historyTag = "history";
    String collectionText = "";
    private final String historyFilePath = "/data/data/com.meng.mbrowser/history.xml";
    private final String collectionTag = "collection";
    String historyText = "";
    xmlParser xmlparser;

    public HistoryAndCollectionTool(Context c) {
        context = c;
        historyText = readHistory();
        collectionText = readCollection();
        xmlparser = new xmlParser();
    }

    private String readHistory() {
        return historyText = tool.readTextFile(historyFilePath, "historys");
    }

    private String readCollection() {
        return collectionText = tool.readTextFile(collectionFilePath, "collections");
    }

    public void addHistory(String s) {
       tool.saveTextFile(historyFilePath, tool.insertText(readHistory(), "\n <history value=\"" + s + "\" />", "<historys>"));
    }

    public void addCollection(String s) {
        tool.saveTextFile(collectionFilePath, tool.insertText(readCollection(), "\n <collection value=\"" + s + "\" />", "<collections>"));
    }
    public String[] getHistory(){
        try{
            return xmlparser.parseXml(historyFilePath,historyTag);
        }catch(Exception e){
            Log.e(getClass().getName(),e.toString());
            return new String[]{"读取出错",e.toString()};
        }
    }
    public String[] getCollection() {
        try {
            return xmlparser.parseXml(collectionFilePath, collectionTag);
        } catch (Exception e) {
            Log.e(getClass().getName(), e.toString());
            return new String[]{"读取出错", e.toString()};
        }
    }
    public void cleanHistory(){
        historyText="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<historys>\n</historys>";
        tool.saveTextFile(historyFilePath,historyText);
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
    public void cleanCollection() {
        collectionText = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<collections>\n</collections>";
        tool.saveTextFile(collectionFilePath, collectionText);
    }


}
