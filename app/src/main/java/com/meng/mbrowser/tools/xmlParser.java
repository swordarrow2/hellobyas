package com.meng.mbrowser.tools;

import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class xmlParser {
    String[] values;

    public xmlParser() {
    }

    public int getXmlLength(String path,String tag) {
        return parse(false,path,tag);
    }

    public String[] parseXml(String path, String tag) {
        values = new String[getXmlLength(path,tag) + 1];
        parse(true,path,tag);
        return values;
    }

    private int parse(boolean b,String path, String tag) {
        try {
            int len = 0;
            File xmlFile = new File(path);
            InputStream is = new FileInputStream(xmlFile);
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xrp = fac.newPullParser();
            xrp.setInput(is, "utf-8");
            if (b) {
                while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                        if (xrp.getName().equals(tag)) {
                            values[len] = xrp.getAttributeValue(0);
                            len++;
                        }
                    }
                    xrp.next();
                }
                values[len] = "清除所有条目";
            } else {
                while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                        if (xrp.getName().equals(tag)) {
                            len++;
                        }
                    }
                    xrp.next();
                }
            }
            return len;
        } catch (Exception e) {
            Log.e(getClass().getName(), e.toString());
            return -1;
        }
    }

}

	/*
package com.meng.mbrowser.tools;

import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class xmlParser {
    String[] values;
    String xmlPath;
    String tagName;

    public xmlParser(String path, String tag) {
        xmlPath = path;
        tagName = tag;
    }

    public int getXmlLength() {
        return parse(false);
    }

    public String[] parseXml() {
        values = new String[getXmlLength() + 1];
        parse(true);
        return values;
    }

    private int parse(boolean b) {
        try {
            int len = 0;
            File xmlFile = new File(xmlPath);
            InputStream is = new FileInputStream(xmlFile);
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xrp = fac.newPullParser();
            xrp.setInput(is, "utf-8");
            if (b) {
                while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                        if (xrp.getName().equals(tagName)) {
                            values[len] = xrp.getAttributeValue(0);
                            len++;
                        }
                    }
                    xrp.next();
                }
                values[len] = "清除所有条目";
            } else {
                while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                        if (xrp.getName().equals(tagName)) {
                            len++;
                        }
                    }
                    xrp.next();
                }
            }
            //  while(xrp.getEventType()!=XmlResourceParser.END_DOCUMENT){
            //       if(xrp.getEventType()==XmlResourceParser.START_TAG){
            //             if(xrp.getName().equals(tagName)){
            //                   if(b){
            //                         values[len]=xrp.getAttributeValue(0);
            //                       }
//                        len++;
//                    }
//                }
//                xrp.next();
//            }
            //           if(b){
            //             values[len]="清除所有条目";
            //         }
            return len;
        } catch (Exception e) {
            Log.e(getClass().getName(), e.toString());
            return -1;
        }
    }
}
	 */
