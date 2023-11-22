package com.example.utilsgather.source_file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class BitmapGainUtil {
    /**
     * 从Drawable中获得bitmap
     */
    public static Bitmap getBitmapFromDrawable(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * 从Drawable中获得bitmap
     */
    public static Bitmap getBitmapFromDrawable_2(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
