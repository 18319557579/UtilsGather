package com.example.utilsgather.source_file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class BitmapGainUtil {
    /**
     * 从Drawable中获得bitmap
     */
    public static Bitmap getBitmapFromDrawable(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * 将 Drawable 转为 Bitmap
     */
    public static Bitmap drawableToBitmap(@NonNull Drawable drawable, int width, int height) {
        // 检查Drawable是否是BitmapDrawable的实例，如果是，可以直接调用getBitmap()方法获取Bitmap。
        // 这个将会使用原来Drawable的宽高，这样子我们就无法自定义了，所以这里没必要加这一段代码
        /*if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }*/

        // 为drawable创建一个bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // 将drawable的内容绘制到bitmap上
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 从本地文件中获得Bitmap
     */
    public static Bitmap getBitmapFromLocalFile(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }
}
