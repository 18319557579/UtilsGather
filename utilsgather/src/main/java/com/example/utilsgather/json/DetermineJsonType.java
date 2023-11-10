package com.example.utilsgather.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DetermineJsonType {
    public enum JSON_TYPE {

        OBJECT,  //是json对象类型

        ARRAY,  //是json数组类型

        NONE  //不是json类型
    }

    /**
     * 判断字符串的Json类型
     */
    public static JSON_TYPE getJsonType(String str) {
        Object json = null;
        try {
            json = new JSONTokener(str).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
            return JSON_TYPE.NONE;
        }

        if (json instanceof JSONObject) {
            return JSON_TYPE.OBJECT;
        } else if (json instanceof JSONArray) {
            return JSON_TYPE.ARRAY;
        } else {
            return JSON_TYPE.NONE;
        }
    }
}
