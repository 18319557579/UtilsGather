package com.example.utilsgather.source_file.assets;

import android.content.Context;

import com.example.utilsgather.logcat.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AssetsUtil {
    /**
     * 从assets
     */
    public static String readAssetsFile(Context context, String fileName) {
        String fileStr = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int fileLength = is.available();
            byte[] buffer = new byte[fileLength];
            int readLength = is.read(buffer);
            LogUtil.d("读取的内容长度：" + readLength);

            is.close();
            fileStr = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.d("获取文件内容失败");
        }
        return fileStr;
    }

}
