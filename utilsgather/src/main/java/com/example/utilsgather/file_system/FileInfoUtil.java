package com.example.utilsgather.file_system;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;

public class FileInfoUtil {
    /**
     * 获得图片大小
     */
    public static long getFileSize(Context context, Uri imageUri) {
        String imagePath = imageUri.getPath();
        File imageFile = new File(imagePath);
        return imageFile.length();
    }

    /**
     * 获得图片大小
     *
     * 当从某些来源（如Android的MediaStore）获得图片时，直接使用Uri.getPath()来获取文件路径可能会得到一个不正确的路径，
     * 因此导致File对象不能正确访问文件，从而得到的文件大小为0。要正确处理这种情况，需要使用ContentResolver来获取实际的文件大小
     */
    public static long getFileSizeContentResolver(Context context, Uri imageUri) {
        Cursor cursor = context.getContentResolver().query(imageUri, null, null, null, null);
        long size = 0L;
        //从cursor中获取大小信息
        if (cursor != null && cursor.moveToFirst()) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            if (!cursor.isNull(sizeIndex)) {
                size = cursor.getLong(sizeIndex);
            }
            cursor.close();
        }
        return size;
    }
}
