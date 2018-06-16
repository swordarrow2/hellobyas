package com.meng.mbrowser.camera;

import android.graphics.*;
import android.hardware.*;
import android.os.*;

import android.hardware.Camera;

final class PreviewCallback implements Camera.PreviewCallback{
    private final CameraConfigurationManager configManager;
    private final boolean useOneShotPreviewCallback;
    private Handler previewHandler;
    private int previewMessage;

    PreviewCallback(CameraConfigurationManager configManager,boolean useOneShotPreviewCallback){
        this.configManager=configManager;
        this.useOneShotPreviewCallback=useOneShotPreviewCallback;
    }

    void setHandler(Handler previewHandler,int previewMessage){
        this.previewHandler=previewHandler;
        this.previewMessage=previewMessage;
    }

    public void onPreviewFrame(byte[] data,Camera camera){
        Point cameraResolution = configManager.getCameraResolution();
        if(!useOneShotPreviewCallback){
            camera.setPreviewCallback(null);
        }
        if(previewHandler!=null){
            Message message = previewHandler.obtainMessage(previewMessage,cameraResolution.x,
														   cameraResolution.y,data);
            message.sendToTarget();
            previewHandler=null;
        }else{
        }
    }

}
