package com.meng.mbrowser.decoding;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import com.google.zxing.*;
import com.meng.mbrowser.*;
import com.meng.mbrowser.camera.*;
import com.meng.mbrowser.view.*;
import java.util.*;


public final class CaptureActivityHandler extends Handler{

	private final CaptureActivity activity;
    private final DecodeThread decodeThread;
    private State state;

    public CaptureActivityHandler(CaptureActivity activity,Vector<BarcodeFormat> decodeFormats,
                                  String characterSet){
        this.activity=activity;
        decodeThread=new DecodeThread(activity,decodeFormats,characterSet,
									  new QRScanViewResultPointCallback(activity.getViewfinderView()));
        decodeThread.start();
        state=State.SUCCESS;
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message){
        if(message.what==R.id.auto_focus){
            if(state==State.PREVIEW){
                CameraManager.get().requestAutoFocus(this,R.id.auto_focus);
            }
        }else if(message.what==R.id.restart_preview){
            restartPreviewAndDecode();
        }else if(message.what==R.id.decode_succeeded){
            state=State.SUCCESS;
            Bundle bundle = message.getData();
            Bitmap barcode = bundle==null? null :(Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);
            activity.handleDecode((Result) message.obj,barcode);
        }else if(message.what==R.id.decode_failed){
            state=State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),R.id.decode);
        }else if(message.what==R.id.return_scan_result){
            activity.setResult(Activity.RESULT_OK,(Intent) message.obj);
            activity.finish();
        }else if(message.what==R.id.launch_product_query){
            String url = (String) message.obj;
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            activity.startActivity(intent);
        }
    }

    public void quitSynchronously(){
        state=State.DONE;
        CameraManager.get().stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(),R.id.quit);
        quit.sendToTarget();
        try{
            decodeThread.join();
        }catch(InterruptedException e){
        }
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    private void restartPreviewAndDecode(){
        if(state==State.SUCCESS){
            state=State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),R.id.decode);
            CameraManager.get().requestAutoFocus(this,R.id.auto_focus);
            activity.drawViewfinder();
        }
    }

    private enum State{
        PREVIEW,
        SUCCESS,
        DONE
		}

}
