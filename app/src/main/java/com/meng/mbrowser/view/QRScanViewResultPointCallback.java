package com.meng.mbrowser.view;


import com.google.zxing.*;

public final class QRScanViewResultPointCallback implements ResultPointCallback {

    private final QRScanView viewfinderView;

    public QRScanViewResultPointCallback(QRScanView viewfinderView) {
        this.viewfinderView = viewfinderView;
    }

    public void foundPossibleResultPoint(ResultPoint point) {
        viewfinderView.addPossibleResultPoint(point);
    }

}
