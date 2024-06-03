package com.example.utilsuser.httpurlconnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsuser.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetworkActivity extends AppCompatActivity {
    private NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask();

    private TextView tvUrl = null;
    private TextView tvRequestHeader = null;
    private TextView tvRequestBody = null;
    private TextView tvResponseHeader = null;
    private TextView tvResponseBody = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        tvUrl = findViewById(R.id.tvUrl);
        tvRequestHeader = findViewById(R.id.tvRequestHeader);
        tvRequestBody = findViewById(R.id.tvRequestBody);
        tvResponseHeader = findViewById(R.id.tvResponseHeader );
        tvResponseBody = findViewById(R.id.tvResponseBody );

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String networkAction = intent.getStringExtra("action");
            networkAsyncTask.execute(networkAction);
        }

    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, Map<String, Object>> {
        //NETWORK_GET表示发送GET请求
        public static final String NETWORK_GET = "NETWORK_GET";
        //NETWORK_POST_KEY_VALUE表示用POST发送键值对数据
        public static final String NETWORK_POST_KEY_VALUE = "NETWORK_POST_KEY_VALUE";
        //NETWORK_POST_XML表示用POST发送XML数据
        public static final String NETWORK_POST_XML = "NETWORK_POST_XML";
        //NETWORK_POST_JSON表示用POST发送JSON数据
        public static final String NETWORK_POST_JSON = "NETWORK_POST_JSON";

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            Map<String, Object> result = new HashMap<>();
            URL url = null;  //请求的URL地址
            HttpURLConnection conn = null;
            String requestHeader = null;  //请求头
            byte[] requestBody = null;  //请求体
            String responseHeader = null;   //响应头
            byte[] responseBody = null;  //响应体
            String action = params[0];  //区分类型

            try {
                if (NETWORK_GET.equals(action)) {
                    url = new URL("https://wanandroid.com/article/list/0/json?author=鸿洋");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    //用setRequestProperty方法设置一个自定义的请求头:action，用于后端判断
                    conn.setRequestProperty("myaction", NETWORK_GET);
                    conn.setUseCaches(false);
                    requestHeader = getRequestHeader(conn);

                    //conn.connect()方法不必显式调用，当调用conn.getInputStream()方法时内部也会自动调用connect方法
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    responseBody = getBytesByInputStream(is);
                    responseHeader = getResponseHeader(conn);

                } else if (NETWORK_POST_KEY_VALUE.equals(action)) {
                    url = new URL("https://www.wanandroid.com/user/register");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    //用setRequestProperty方法设置一个自定义的请求头:action，用于后端判断
                    conn.setRequestProperty("myaction", NETWORK_POST_KEY_VALUE);
                    requestHeader = getRequestHeader(conn);

                    OutputStream os = conn.getOutputStream();
                    requestBody = new String("username=ALittleDaisy2&password=987654321&repassword=987654321").getBytes(StandardCharsets.UTF_8);
                    os.write(requestBody);
                    os.flush();
                    os.close();

                    InputStream is = conn.getInputStream();
                    responseBody = getBytesByInputStream(is);
                    responseHeader = getResponseHeader(conn);

                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }

            result.put("url", url.toString());
            result.put("action", action);
            result.put("requestHeader", requestHeader);
            result.put("requestBody", requestBody);
            result.put("responseHeader", responseHeader);
            result.put("responseBody", responseBody);
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);
            String url = (String) result.get("url");
            String action = (String) result.get("action");
            String requestHeader = (String) result.get("requestHeader");
            byte[] requestBody = (byte[]) result.get("requestBody");
            String responseHeader = (String) result.get("responseHeader");
            byte[] responseBody = (byte[]) result.get("responseBody");

            tvUrl.setText(url);

            if (requestHeader != null) {
                tvRequestHeader.setText(requestHeader);
            }
            if (requestBody != null) {
                String request = getStringByBytes(requestBody);
                tvRequestBody.setText(request);
            }
            if (responseHeader != null) {
                tvResponseHeader.setText(responseHeader);
            }
            if (NETWORK_GET.equals(action)) {
                String response = getStringByBytes(responseBody);
                tvResponseBody.setText(response);

            } else if (NETWORK_POST_KEY_VALUE.equals(action)) {
                String response = getStringByBytes(responseBody);
                tvResponseBody.setText(response);
            }
        }
    }

    /**
     * 获取请求头
     */
    private String getRequestHeader(HttpURLConnection conn) {
        Map<String, List<String>> requestHeaderMap = conn.getRequestProperties();
        Iterator<String> requestHeaderIterator = requestHeaderMap.keySet().iterator();  //只拿key
        StringBuilder sbRequestHeader = new StringBuilder();
        while (requestHeaderIterator.hasNext()) {
            String requestHeaderKey = requestHeaderIterator.next();  //key
            String requestHeaderValue = conn.getRequestProperty(requestHeaderKey);  //value
            sbRequestHeader.append(requestHeaderKey)
                    .append(":")
                    .append(requestHeaderValue)
                    .append("\n");
        }
        return sbRequestHeader.toString();
    }

    /**
     * 获取相应头
     */
    private String getResponseHeader(HttpURLConnection conn) {
        Map<String, List<String>> responseHeaderMap = conn.getHeaderFields();
        int size = responseHeaderMap.size();
        StringBuilder sbResponseHeader = new StringBuilder();
        for (int i = 0; i < size; i++) {
            String responseHeaderKey = conn.getHeaderFieldKey(i);  //key
            String responseHeaderValue = conn.getHeaderField(i);  //value
            sbResponseHeader.append(responseHeaderKey)
                    .append(":")
                    .append(responseHeaderValue)
                    .append("\n");
        }
        return sbResponseHeader.toString();
    }

    /**
     * 从InputStream中读取数据，转换成byte数组，最后关闭InputStream
     */
    private byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);

        byte[] buffer = new byte[1024 * 8];
        int length = 0;

        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    /**
     * 根据字节数组构建UTF-8字符串
     */
    private String getStringByBytes(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}