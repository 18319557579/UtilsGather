package com.example.utilsgather.json;

import com.google.gson.Gson;

public class ParseJsonByGson {
    /**
     * 用Gson来解析json文件到实体类
     */
    public static <T> T parse(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }
}
