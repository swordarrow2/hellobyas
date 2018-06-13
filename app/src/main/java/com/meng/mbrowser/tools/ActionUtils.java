package com.meng.mbrowser.tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class ActionUtils {

    public static final int PHOTO_REQUEST_GALLERY = 1000;
    public static void startActivityForGallery(Activity activity, int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, requestCode);
    }


}
