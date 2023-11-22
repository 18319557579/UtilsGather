package com.example.utilsgather.source_file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DrawableGainUtil {
    /**
     * 用Bitmap生成Drawable
     */
    public static Drawable getDrawableFromBitmap(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
