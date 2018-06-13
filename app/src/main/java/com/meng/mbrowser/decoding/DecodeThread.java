package com.meng.mbrowser.decoding;

import android.os.*;
import com.google.zxing.*;
import com.google.zxing.qrcode.decoder.DecodeHintType;
import com.meng.mbrowser.CaptureActivity;

import java.util.*;
import java.util.concurrent.*;


final class DecodeThread extends Thread{

    public static final String BARCODE_BITMAP = "barcode_bitmap";
    private final CaptureActivity activity;
    private final Hashtable<DecodeHintType, Object> hints;
    private final CountDownLatch handlerInitLatch;
    private Handler handler;

    DecodeThread(CaptureActivity activity,
                 Vector<BarcodeFormat> decodeFormats,
                 String characterSet,
                 ResultPointCallback resultPointCallback){

        this.activity=activity;
        handlerInitLatch=new CountDownLatch(1);

        hints=new Hashtable<DecodeHintType, Object>(3);

        if(decodeFormats==null||decodeFormats.isEmpty()){
            decodeFormats=new Vector<BarcodeFormat>();
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }

        hints.put(DecodeHintType.POSSIBLE_FORMATS,decodeFormats);

        if(characterSet!=null){
            hints.put(DecodeHintType.CHARACTER_SET,characterSet);
        }

        hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK,resultPointCallback);
    }

    Handler getHandler(){
        try{
            handlerInitLatch.await();
        }catch(InterruptedException ie){
        }
        return handler;
    }

    @Override
    public void run(){
        Looper.prepare();
        handler=new DecodeHandler(activity,hints);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
