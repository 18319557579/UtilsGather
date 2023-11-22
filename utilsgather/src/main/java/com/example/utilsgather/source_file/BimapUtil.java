package com.example.utilsgather.source_file;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BimapUtil {
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
}
