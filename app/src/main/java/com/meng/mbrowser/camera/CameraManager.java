package com.meng.mbrowser.camera;

import android.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.util.*;
import android.view.*;
import com.meng.mbrowser.view.*;
import java.io.*;
import java.util.*;

import android.hardware.Camera;

public final class CameraManager{

    static final int SDK_INT; // Later we can use Build.VERSION.SDK_INT
    private static final String TAG = CameraManager.class.getSimpleName();
    private static final int MIN_FRAME_WIDTH = 240;
    private static final int MIN_FRAME_HEIGHT = 240;
    private static final int MAX_FRAME_WIDTH = 675;
    private static final int MAX_FRAME_HEIGHT = 675; // = 5/8 * 1080
    private static CameraManager cameraManager;

    static {
        int sdkInt;
        try{
            sdkInt=Integer.parseInt(Build.VERSION.SDK);
        }catch(NumberFormatException nfe){
            // Just to be safe
            sdkInt=10000;
        }
        SDK_INT=sdkInt;
    }

    private final Context context;
    private final CameraConfigurationManager configManager;
    private final boolean useOneShotPreviewCallback;
    private final PreviewCallback previewCallback;
    private final AutoFocusCallback autoFocusCallback;
    private Camera camera;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private boolean previewing;

    private CameraManager(Context context){
        this.context=context;
        this.configManager=new CameraConfigurationManager(context);
        useOneShotPreviewCallback=Integer.parseInt(Build.VERSION.SDK)>3; // 3 = Cupcake
        previewCallback=new PreviewCallback(configManager,useOneShotPreviewCallback);
        autoFocusCallback=new AutoFocusCallback();
    }

    public static void init(Context context){
        if(cameraManager==null){
            cameraManager=new CameraManager(context);
        }
    }

    public static CameraManager get(){
        return cameraManager;
    }

    public void openDriver(SurfaceHolder holder) throws IOException{
        if(camera==null){
            camera=Camera.open();
            if(camera==null){
                throw new IOException();
            }
            camera.setPreviewDisplay(holder);
            if(!initialized){
                initialized=true;
                configManager.initFromCameraParameters(camera);
            }
            configManager.setDesiredCameraParameters(camera);
            //FIXME
            FlashlightManager.enableFlashlight();
        }
    }

    public void closeDriver(){
        if(camera!=null){
            FlashlightManager.disableFlashlight();
            camera.release();
            camera=null;
        }
    }

    public void startPreview(){
        if(camera!=null&&!previewing){
            camera.startPreview();
            previewing=true;
        }
    }

    public void stopPreview(){
        if(camera!=null&&previewing){
            if(!useOneShotPreviewCallback){
                camera.setPreviewCallback(null);
            }
            camera.stopPreview();
            previewCallback.setHandler(null,0);
            autoFocusCallback.setHandler(null,0);
            previewing=false;
        }
    }

    public void requestPreviewFrame(Handler handler,int message){
        if(camera!=null&&previewing){
            previewCallback.setHandler(handler,message);
            if(useOneShotPreviewCallback){
                camera.setOneShotPreviewCallback(previewCallback);
            }else{
                camera.setPreviewCallback(previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler,int message){
        if(camera!=null&&previewing){
            autoFocusCallback.setHandler(handler,message);
            //Log.d(TAG, "Requesting auto-focus callback");
            camera.autoFocus(autoFocusCallback);
        }
    }

    /**
     * Calculates the framing rect which the UI should draw to show the user where to place the
     * barcode. This target helps with alignment as well as forces the user to hold the device
     * far enough away to ensure the image will be in focus.
     *
     * @return The rectangle to draw on screen in window coordinates.
     *
     * 获取扫描框显示位置 默认位置为屏幕中间
     */
    public Rect getFramingRect(int offsetX,int offsetY){
        Point screenResolution = configManager.getScreenResolution();
        if(framingRect==null){
            // 魅族等机型拒绝camera权限后screenResolution返回null
            if(screenResolution==null){
                return null;
            }
            if(camera==null){
                return null;
            }
            int width = screenResolution.x*3/4;
            if(width<MIN_FRAME_WIDTH){
                width=MIN_FRAME_WIDTH;
            }else if(width>MAX_FRAME_WIDTH){
                width=MAX_FRAME_WIDTH;
            }
            int height = screenResolution.y*3/4;
            if(height<MIN_FRAME_HEIGHT){
                height=MIN_FRAME_HEIGHT;
            }else if(height>MAX_FRAME_HEIGHT){
                height=MAX_FRAME_HEIGHT;
            }
            int leftOffset = (screenResolution.x-width)/2;
            int topOffset = (screenResolution.y-height)/2;
            framingRect=new Rect(leftOffset+offsetX,
								 topOffset+offsetY,
								 leftOffset+width+offsetX,
								 topOffset+height+offsetY);
            Log.d(TAG,"Calculated framing rect: "+framingRect);
        }
        return framingRect;
    }

    /**
     * Like {@link #getFramingRect} but coordinates are in terms of the preview frame,
     * not UI / screen.
     *
     * 获取扫描实际有效区域
     */
    public Rect getFramingRectInPreview(){
        if(framingRectInPreview==null){
            Rect rect = new Rect(getFramingRect(QRScanView.RECT_OFFSET_X,QRScanView.RECT_OFFSET_Y));
            Point cameraResolution = configManager.getCameraResolution();
            Point screenResolution = configManager.getScreenResolution();
            //modify here
//      rect.left = rect.left * cameraResolution.x / screenResolution.x;
//      rect.right = rect.right * cameraResolution.x / screenResolution.x;
//      rect.top = rect.top * cameraResolution.y / screenResolution.y;
//      rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
            rect.left=rect.left*cameraResolution.y/screenResolution.x;
            rect.right=rect.right*cameraResolution.y/screenResolution.x;
            rect.top=rect.top*cameraResolution.x/screenResolution.y;
            rect.bottom=rect.bottom*cameraResolution.x/screenResolution.y;
            framingRectInPreview=rect;
        }
        return framingRectInPreview;
    }

    /**
     * Converts the result points from still resolution coordinates to screen coordinates.
     *
     * @param points The points returned by the Reader subclass through Result.getResultPoints().
     * @return An array of Points scaled to the size of the framing rect and offset appropriately
     *         so they can be drawn in screen coordinates.
     */
	/*
	 public Point[] convertResultPoints(ResultPoint[] points) {
	 Rect frame = getFramingRectInPreview();
	 int count = points.length;
	 Point[] output = new Point[count];
	 for (int x = 0; x < count; x++) {
	 output[x] = new Point();
	 output[x].x = frame.left + (int) (points[x].getX() + 0.5f);
	 output[x].y = frame.top + (int) (points[x].getY() + 0.5f);
	 }
	 return output;
	 }
	 */

    /**
     * A factory method to build the appropriate LuminanceSource object based on the format
     * of the preview buffers, as described by Camera.Parameters.
     *
     * @param data   A preview frame.
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return A PlanarYUVLuminanceSource instance.
     */
    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data,int width,int height){
        Rect rect = getFramingRectInPreview();
        int previewFormat = configManager.getPreviewFormat();
        String previewFormatString = configManager.getPreviewFormatString();
        switch(previewFormat){
				// This is the standard Android format which all devices are REQUIRED to support.
				// In theory, it's the only one we should ever care about.
            case PixelFormat.YCbCr_420_SP:
                // This format has never been seen in the wild, but is compatible as we only care
                // about the Y channel, so allow it.
            case PixelFormat.YCbCr_422_SP:
                return new PlanarYUVLuminanceSource(data,width,height,rect.left,rect.top,
													rect.width(),rect.height());
            default:
                // The Samsung Moment incorrectly uses this variant instead of the 'sp' version.
                // Fortunately, it too has all the Y data up front, so we can read it.
                if("yuv420p".equals(previewFormatString)){
                    return new PlanarYUVLuminanceSource(data,width,height,rect.left,rect.top,
														rect.width(),rect.height());
                }
        }
        throw new IllegalArgumentException("Unsupported picture format: "+
										   previewFormat+'/'+previewFormatString);
    }

    public Context getContext(){
        return context;
    }

    /**
     * 打开或关闭闪光灯
     *
     * @param open 控制是否打开
     * @return 打开或关闭失败，则返回false。
     */
    public boolean setFlashLight(boolean open){
        if(camera==null){
            return false;
        }
        Camera.Parameters parameters = camera.getParameters();
        if(parameters==null){
            return false;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if(null==flashModes||0==flashModes.size()){
            // Use the screen as a flashlight (next best thing)
            return false;
        }
        String flashMode = parameters.getFlashMode();
        if(open){
            if(Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)){
                return true;
            }
            // Turn on the flash
            if(flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                return true;
            }else{
                return false;
            }
        }else{
            if(Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)){
                return true;
            }
            // Turn on the flash
            if(flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                return true;
            }else
                return false;
        }
    }

    /**
     * 检查是否获得摄像头权限
     * @return
     */
    public int checkCameraPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            return context.checkSelfPermission(Manifest.permission.CAMERA);
        }else{
            if(camera==null){
                return PackageManager.PERMISSION_DENIED;
            }
        }
        return PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否已开启预览模式
     * @return
     */
    public boolean isPreviewing(){
        return previewing;
    }

}
