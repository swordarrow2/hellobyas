package com.meng.mbrowser.tools;

import android.content.res.*;
import java.io.*;
import org.xmlpull.v1.*;

public class xmlParser{
    String[] values;
    boolean parsered = false;
	String xmlPath;
	String tagName;
    public xmlParser(String path,String tag){
        xmlPath=path;
		tagName=tag;
    }

    public int getXmlLength() throws Exception{
        return parse(false);
    }

	public String[] parseXml() throws Exception{
		values=new String[getXmlLength()];
		parse(true);
		return values;
	}

    public boolean isParsedSeccuss(){
        return parsered;
    }


    private int parse(boolean b) throws Exception{
		int len=0;
            File xmlFile = new File(xmlPath);
            InputStream is = new FileInputStream(xmlFile);
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xrp = fac.newPullParser();
            xrp.setInput(is,"utf-8");
            while(xrp.getEventType()!=XmlResourceParser.END_DOCUMENT){
                if(xrp.getEventType()==XmlResourceParser.START_TAG){
                    if(xrp.getName().equals(tagName)){
						if(b) {
                            values[len] = xrp.getAttributeValue(0);
                        }
							len++;
                    }
                }
                xrp.next();
            }
		return len;
    }

}

	
