package com.example.uioperate.storage.image_selector;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import com.example.utilsgather.context.ApplicationGlobal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<ImageBean> initAllImgInThePhone(SelectionBean selectionBean) {

        List<ImageBean> imgList = new ArrayList<>();

        Cursor cursor = ApplicationGlobal.getInstance().getContentResolver().query(
                selectionBean.uri, selectionBean.projection, selectionBean.selection, selectionBean.selectionArgs,
                selectionBean.sortOrder);
        List<String> dateList = new ArrayList<>();//存日期,因为照片按分组
        while (cursor.moveToNext()) {
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String dataStr = new String(data, 0, data.length - 1);//图片在手机里的路径
            File file = new File(dataStr);
            long time = file.lastModified(); //记录此图片的上次修改时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(time); // 2019-04-10
            StringBuffer imgPath = new StringBuffer("file://").append(dataStr);
            ImageBean bean = new ImageBean(dataStr, dateStr, imgPath.toString());

            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            bean.setUri(deleteUri);

            imgList.add(bean);
            if (!dateList.contains(dateStr)) {
                dateList.add(dateStr);
            }
        }

        return imgList;
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_CLOSE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 将Base64编码转换为图片
     * @param base64Str
     * @param path
     * @return true
     */
    public static boolean base64ToFile(String base64Str,String path) {
        byte[] data = Base64.decode(base64Str,Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if(data[i] < 0){
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
