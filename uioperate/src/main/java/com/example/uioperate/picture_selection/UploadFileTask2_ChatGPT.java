package com.example.uioperate.picture_selection;

import android.content.Context;
import android.net.Uri;

import com.example.utilsgather.logcat.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UploadFileTask2_ChatGPT {
    /**
     * 上传文件到服务器，并返回服务器相应结果
     * @param requestURL 服务器的地址
     * @param imageUri 文件的Uri
     * @param context
     * @return 服务器返回的结果
     */
    public String uploadImage(String requestURL, Uri imageUri, Context context) {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        // boundary就是request头和上传文件内容的分隔符(可自定义任意一组字符串)
        String boundary = "*****" + System.currentTimeMillis() + "*****";
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        try {
            InputStream fileInputStream = context.getContentResolver().openInputStream(imageUri);
            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + imageUri.getLastPathSegment() + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            int bytesAvailable = fileInputStream.available();
            int bufferSize = Math.min(bytesAvailable, 1024 * 1024);
            byte[] buffer = new byte[bufferSize];

            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer, 0, bufferSize)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outputStream.flush();
            fileInputStream.close();
            outputStream.close();

            // 检查服务器响应
            int serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();

            if (serverResponseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                String result = convertStreamToString(inputStream);
                LogUtil.d("Upload Success " + "Response: " + result);
                inputStream.close();

                return result;
            } else {
                LogUtil.d("Upload Error" + "Response Code: " + serverResponseCode + " Message: " + serverResponseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "";
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
