package com.meng.mbrowser.decoding;

import android.content.*;
import android.net.*;
import com.google.zxing.*;
import java.util.*;
import java.util.regex.*;

final class DecodeFormatManager{

    static final Vector<BarcodeFormat> PRODUCT_FORMATS;
    static final Vector<BarcodeFormat> ONE_D_FORMATS;
    static final Vector<BarcodeFormat> QR_CODE_FORMATS;
    static final Vector<BarcodeFormat> DATA_MATRIX_FORMATS;

    static {
        PRODUCT_FORMATS=new Vector<BarcodeFormat>(5);
        PRODUCT_FORMATS.add(BarcodeFormat.UPC_A);
        PRODUCT_FORMATS.add(BarcodeFormat.UPC_E);
        PRODUCT_FORMATS.add(BarcodeFormat.EAN_13);
        PRODUCT_FORMATS.add(BarcodeFormat.EAN_8);
        PRODUCT_FORMATS.add(BarcodeFormat.RSS_14);
        ONE_D_FORMATS=new Vector<BarcodeFormat>(PRODUCT_FORMATS.size()+4);
        ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
        ONE_D_FORMATS.add(BarcodeFormat.CODE_39);
        ONE_D_FORMATS.add(BarcodeFormat.CODE_93);
        ONE_D_FORMATS.add(BarcodeFormat.CODE_128);
        ONE_D_FORMATS.add(BarcodeFormat.ITF);
        QR_CODE_FORMATS=new Vector<BarcodeFormat>(1);
        QR_CODE_FORMATS.add(BarcodeFormat.QR_CODE);
        DATA_MATRIX_FORMATS=new Vector<BarcodeFormat>(1);
        DATA_MATRIX_FORMATS.add(BarcodeFormat.DATA_MATRIX);
    }

    private DecodeFormatManager(){
    }

    private static Vector<BarcodeFormat> parseDecodeFormats(Iterable<String> scanFormats,
                                                            String decodeMode){
        if(scanFormats!=null){
            Vector<BarcodeFormat> formats = new Vector<BarcodeFormat>();
            try{
                for(String format : scanFormats){
                    formats.add(BarcodeFormat.valueOf(format));
                }
                return formats;
            }catch(IllegalArgumentException iae){
            }
        }
        if(decodeMode!=null){
            if(Intents.Scan.PRODUCT_MODE.equals(decodeMode)){
                return PRODUCT_FORMATS;
            }
            if(Intents.Scan.QR_CODE_MODE.equals(decodeMode)){
                return QR_CODE_FORMATS;
            }
            if(Intents.Scan.DATA_MATRIX_MODE.equals(decodeMode)){
                return DATA_MATRIX_FORMATS;
            }
            if(Intents.Scan.ONE_D_MODE.equals(decodeMode)){
                return ONE_D_FORMATS;
            }
        }
        return null;
    }

}
