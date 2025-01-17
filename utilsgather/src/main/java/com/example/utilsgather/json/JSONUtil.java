package com.example.utilsgather.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

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

    /**
     * 将Collection类型转为JSONArray
     * 原理是利用Collection中的迭代器，遍历其中的元素，作为JSON数组的元素
     */
    public static String listToJSONArray(Collection<?> list) {
        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();
    }
}
