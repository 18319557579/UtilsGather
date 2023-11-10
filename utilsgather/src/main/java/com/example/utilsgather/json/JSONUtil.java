package com.example.utilsgather.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
    /**
     * 判断该json数据中是否包含keyword键
     */
    public static boolean keyIsIncluded(String json, String keyword) {
        try {
            JSONObject jo = new JSONObject(json);
            return jo.has(keyword);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
