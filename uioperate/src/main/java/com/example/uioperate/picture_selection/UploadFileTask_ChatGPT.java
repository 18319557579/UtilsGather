package com.example.uioperate.picture_selection;

import android.content.Context;
import android.net.Uri;

import com.example.utilsgather.logcat.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UploadFileTask_ChatGPT {
    public String uploadImage(String requestURL, Uri imageUri, Context context) {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; // 1MB

        try {
            // 将Uri转换为File路径（示例中需要调整为实际情况）
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[maxBufferSize];
            int count;
            while ((count = imageStream.read(data, 0, data.length)) != -1) {
                baos.write(data, 0, count);
            }
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            imageStream.close();
            baos.close();

            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("uploaded_file", imageUri.getLastPathSegment());

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + imageUri.getLastPathSegment() + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            outputStream.write(imageBytes);

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            outputStream.flush();
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
