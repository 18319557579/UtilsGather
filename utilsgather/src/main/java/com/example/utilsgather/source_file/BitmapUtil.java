package com.example.utilsgather.source_file;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

public class BitmapUtil {
    /**
     * 修改bitmap的宽高
     */
    public static Bitmap zoomBitmap(Bitmap bmp, int newWidth, int newHeight) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        float scaleWidth = (float) newWidth / width;
        float scaleHeight = (float) newHeight / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
    }

    /**
     * 获得Bitmap所占的内存大小
     * 根据不同的SDK版本来使用不同的计算方法
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
