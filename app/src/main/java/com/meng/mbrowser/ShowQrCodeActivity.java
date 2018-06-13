package com.meng.mbrowser;

import android.app.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.widget.*;
import com.meng.mbrowser.tools.*;

public class ShowQrCodeActivity extends Activity{
    ImageView QRCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); 
		setContentView(R.layout.qr_code_view);
        QRCodeImageView =(ImageView)findViewById(R.id.qr_code_viewImageView);
        QRCodeImageView.setImageBitmap(QRCode.createQRCode(getIntent().getStringExtra("url")));
		/*     qrcode2.setImageBitmap(QRCode.createQRCodeWithLogo2("http://www.jianshu.com/users/4a4eb4feee62/latest_articles", 500, drawableToBitmap(getResources().getDrawable(R.drawable.head))));
		 qrcode3.setImageBitmap(QRCode.createQRCodeWithLogo3("http://www.jianshu.com/users/4a4eb4feee62/latest_articles", 500, drawableToBitmap(getResources().getDrawable(R.drawable.head))));
		 qrcode4.setImageBitmap(QRCode.createQRCodeWithLogo4("http://www.jianshu.com/users/4a4eb4feee62/latest_articles", 500, drawableToBitmap(getResources().getDrawable(R.drawable.head))));
		 qrcode5.setImageBitmap(QRCode.createQRCodeWithLogo5("http://www.jianshu.com/users/4a4eb4feee62/latest_articles", 500, drawableToBitmap(getResources().getDrawable(R.drawable.head))));
		 qrcode6.setImageBitmap(QRCode.createQRCodeWithLogo6("http://www.jianshu.com/users/4a4eb4feee62/latest_articles", 500, drawableToBitmap(getResources().getDrawable(R.drawable.head))));
		 */
	}

    public static Bitmap drawableToBitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
