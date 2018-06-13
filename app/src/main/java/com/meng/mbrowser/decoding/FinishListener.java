package com.meng.mbrowser.decoding;

import android.app.*;
import android.content.*;

public final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable{

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish){
        this.activityToFinish=activityToFinish;
    }

    public void onCancel(DialogInterface dialogInterface){
        run();
    }

    public void onClick(DialogInterface dialogInterface,int i){
        run();
    }

    public void run(){
        activityToFinish.finish();
    }

}
