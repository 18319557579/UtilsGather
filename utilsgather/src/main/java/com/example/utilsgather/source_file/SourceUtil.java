package com.example.utilsgather.source_file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
}
