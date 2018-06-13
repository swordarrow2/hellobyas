package com.meng.mbrowser.tools;

import android.graphics.*;
import com.google.zxing.*;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.*;
import com.google.zxing.qrcode.decoder.DecodeHintType;

import java.util.*;


public class QrUtils{
    private static byte[] yuvs;

    public static byte[] getYUV420sp(int inputWidth,int inputHeight,Bitmap scaled){
        int[] argb = new int[inputWidth*inputHeight];
        scaled.getPixels(argb,0,inputWidth,0,0,inputWidth,inputHeight);
        //需要转换成偶数的像素点，否则编码YUV420的时候有可能导致分配的空间大小不够而溢出。
        int requiredWidth = inputWidth%2==0? inputWidth :inputWidth+1;
        int requiredHeight = inputHeight%2==0? inputHeight :inputHeight+1;
        int byteLength = requiredWidth*requiredHeight*3/2;
        if(yuvs==null||yuvs.length<byteLength){
            yuvs=new byte[byteLength];
        }else{
            Arrays.fill(yuvs,(byte) 0);
        }
        encodeYUV420SP(yuvs,argb,inputWidth,inputHeight);
        scaled.recycle();
        return yuvs;
    }

    private static void encodeYUV420SP(byte[] yuv420sp,int[] argb,int width,int height){
        // 帧图片的像素大小
        final int frameSize = width*height;
        int Y, U, V;
        int yIndex = 0;
        int uvIndex = frameSize;
        int R, G, B;
        int argbIndex = 0;
        for(int j = 0; j<height; j++){
            for(int i = 0; i<width; i++){
                // a is not used obviously
                // a = (argb[argbIndex] & 0xff000000) >> 24;
                R=(argb[argbIndex]&0xff0000)>>16;
                G=(argb[argbIndex]&0xff00)>>8;
                B=(argb[argbIndex]&0xff);
                argbIndex++;
                Y=((66*R+129*G+25*B+128)>>8)+16;
                U=((-38*R-74*G+112*B+128)>>8)+128;
                V=((112*R-94*G-18*B+128)>>8)+128;
                Y=Math.max(0,Math.min(Y,255));
                U=Math.max(0,Math.min(U,255));
                V=Math.max(0,Math.min(V,255));
                yuv420sp[yIndex++]=(byte) Y;
                if((j%2==0)&&(i%2==0)){
                    yuv420sp[uvIndex++]=(byte) V;
                    yuv420sp[uvIndex++]=(byte) U;
                }
            }
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height>reqHeight||width>reqWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while((halfHeight/inSampleSize)>reqHeight&&(halfWidth/inSampleSize)>reqWidth){
                inSampleSize*=2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String imgPath,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(imgPath,options);
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFile(imgPath,options);
    }

    public static Result decodeImage(final String path){
        Bitmap bitmap = QrUtils.decodeSampledBitmapFromFile(path,256,256);
        // Google Photo 相册中选取云照片是会出现 Bitmap == null
        if(bitmap==null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        PlanarYUVLuminanceSource source1 = new PlanarYUVLuminanceSource(getYUV420sp(width,height,bitmap),width,height,0,0,width,height,false);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source1));
        HashMap<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.TRY_HARDER,Boolean.TRUE);
        hints.put(DecodeHintType.CHARACTER_SET,"UTF-8");
        try{
            return new MultiFormatReader().decode(binaryBitmap,hints);
        }catch(NotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
