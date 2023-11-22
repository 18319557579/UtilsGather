package com.example.utilsgather.source_file.raw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.RawRes;

import com.example.utilsgather.R;

import java.io.InputStream;

public class SourceUtil {
    /**
     * 从raw中读出图片
     */
    public static Bitmap getBitmapFromRaw(Context context, @RawRes int id) {
        //由于io流的操作属于耗时操作，因此最好在子线程中进行。后面要将图片设置到ImageView时再切到UI线程即可
        InputStream is = context.getResources().openRawResource(id);
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 从raw中读出图片
     * 这种方式得到bitmap长宽像素为原来图片的大小，不因为手机分辨率变化
     */
    public static Bitmap getBitmapFromRaw_2(Context context, @RawRes int id) {
        Resources r = context.getResources();
        InputStream is = r.openRawResource(id);
        BitmapDrawable bmpDrawable = new BitmapDrawable(r, is);
        return bmpDrawable.getBitmap();
    }
}
