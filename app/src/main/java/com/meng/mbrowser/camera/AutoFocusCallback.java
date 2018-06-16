package com.meng.mbrowser.camera;

import android.hardware.*;
import android.os.*;

final class AutoFocusCallback implements Camera.AutoFocusCallback{
    private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
    private Handler autoFocusHandler;
    private int autoFocusMessage;

    void setHandler(Handler autoFocusHandler,int autoFocusMessage){
        this.autoFocusHandler=autoFocusHandler;
        this.autoFocusMessage=autoFocusMessage;
    }

    public void onAutoFocus(boolean success,Camera camera){
        if(autoFocusHandler!=null){
            Message message = autoFocusHandler.obtainMessage(autoFocusMessage,success);
            autoFocusHandler.sendMessageDelayed(message,AUTOFOCUS_INTERVAL_MS);
            autoFocusHandler=null;
        }else{
        }
    }

}
