package com.example.utilsgather.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtil {
    /**
     * 将实体类转为JSON字符串
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 用Gson来解析json文件到实体类
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * 将Json字符串转为List<T>
     * @param jsonArrayString Json字符串
     * @param clazz 实体类型（即List包裹的类型）
     * @return List
     */
    public static <T> List<T> fromJsonToList(String jsonArrayString, Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArrayString, listType);
    }
}
