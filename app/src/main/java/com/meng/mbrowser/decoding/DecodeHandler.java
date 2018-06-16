package com.meng.mbrowser.decoding;

import android.os.*;
import com.google.zxing.*;
import com.google.zxing.common.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.camera.*;
import java.util.*;

import com.meng.mbrowser.camera.PlanarYUVLuminanceSource;



final class DecodeHandler extends Handler{

    private final CaptureActivity activity;
    private final MultiFormatReader multiFormatReader;
    DecodeHandler(CaptureActivity activity,Hashtable<DecodeHintType, Object> hints){
        multiFormatReader=new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.activity=activity;
    }

    @Override
    public void handleMessage(Message message){
        if(message.what==R.id.decode){
            decode((byte[]) message.obj,message.arg1,message.arg2);
        }else if(message.what==R.id.quit){
            Looper.myLooper().quit();
        }
    }

    private void decode(byte[] data,int width,int height){
        long start = System.currentTimeMillis();
        Result rawResult = null;
        byte[] rotatedData = new byte[data.length];
        for(int y = 0; y<height; y++){
            for(int x = 0; x<width; x++)
                rotatedData[x*height+height-y-1]=data[x+y*width];
        }
        int tmp = width; // Here we are swapping, that's the difference to #11
        width=height;
        height=tmp;
        PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData,width,height);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try{
            rawResult=multiFormatReader.decodeWithState(bitmap);
        }catch(ReaderException re){
        }finally{
            multiFormatReader.reset();
        }
        if(rawResult!=null){
            long end = System.currentTimeMillis();
            Message message = Message.obtain(activity.getHandler(),R.id.decode_succeeded,rawResult);
            Bundle bundle = new Bundle();
            bundle.putParcelable(DecodeThread.BARCODE_BITMAP,source.renderCroppedGreyscaleBitmap());
            message.setData(bundle);
            message.sendToTarget();
        }else{
            Message message = Message.obtain(activity.getHandler(),R.id.decode_failed);
            message.sendToTarget();
        }
    }

}
