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

import com.example.utilsgather.logcat.LogUtil;
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

/**
 * 原文：https://juejin.cn/post/6844903828538523655#heading-8
 *
 * 上面我们通过demo演示了如何上传XML文件和JSON文件，并对二者进行解析。在上传的过程中，Android要写入Content-Length这个请求头，Content-Length就是请求体的字节长度，注意是字节长度，而不是字符长度（汉字等会占用两个字节）。默认情况下，Android为了得到Content-Length的长度，Android会把请求体放到内存中的，直到输出流调用了close方法后，才会读取内存中请求体的字节长度，将其作为请求头Content-Length。当要上传的请求体很大时，这会非常占用内存，为此Android提供了两个方法来解决这个问题。
 *
 * setFixedLengthStreamingMode (int contentLength) 如果请求体的大小是知道的，那么可以调用HttpURLConnection的setFixedLengthStreamingMode (int contentLength) 方法，该方法会告诉Android要传输的请求头Content-Length的大小，这样Android就无需读取整个请求体的大小，从而不必一下将请求体全部放到内存中，这样就避免了请求体占用巨大内存的问题。
 *
 * setChunkedStreamingMode (int chunkLength) 如果请求体的大小不知道，那么可以调用setChunkedStreamingMode (int chunkLength)方法。该方法将传输的请求体分块传输，即将原始的数据分成多个数据块，chunkLength表示每块传输的字节大小。比如我们要传输的请求体大小是10M，我们将chunkLength设置为1024 * 1024 byte，即1M，那么Android会将请求体分10次传输，每次传输1M，具体的传输规则是：每次传输一个数据块时，首先在一行中写明该数据块的长度，比如1024
 *  * 1024，然后在后面的一行中写入要传输的数据块的字节数组，再然后是一个空白行，这样第一数据块就这样传输，在空白行之后就是第二个数据块的传输，与第一个数据块的格式一样，直到最后没有数据块要传输了，就在用一行写明要传输的字节为0，这样在服务器端就知道读取完了整个请求体了。
 * 如果设置的chunkLength的值为0，那么表示Android会使用默认的一个值作为实际的chunkLength。
 * 使用setChunkedStreamingMode方法的前提是服务器支持分块数据传输，分块数据传输是从HTTP 1.1开始支持的，所以如果你的服务器只支持HTTP 1.0的话，那么不能使用setChunkedStreamingMode方法。
 */
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
        //发送GET请求，并解析获得的JSON数据
        public static final String NETWORK_GET_PARSE_JSON = "NETWORK_GET_PARSE_JSON";
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
                if (NETWORK_GET.equals(action) || NETWORK_GET_PARSE_JSON.equals(action)) {
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

            } else if (NETWORK_GET_PARSE_JSON.equals(action)) {
                String response = parseJsonResultByBytes(responseBody);
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

    private String parseJsonResultByBytes(byte[] bytes) {
        String jsonString = getStringByBytes(bytes);
        ArticleBean articleBean = JsonParser.parse(jsonString);
        return articleBean.toString();
    }
}