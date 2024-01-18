package com.example.utilsgather.sp;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.utilsgather.context.ApplicationGlobal;

public class SpUtil {
    public static void putString(String _sFile, String _sKey,String _sData) {
        if (_sKey != null && _sData != null) {
            SharedPreferences sp = ApplicationGlobal.getInstance().getSharedPreferences(_sFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(_sKey, _sData);
            editor.commit();
        }
    }

    public static String getString(String _sFile, String _sKey, String defValue) {
        if (_sKey != null) {
            SharedPreferences sp = ApplicationGlobal.getInstance().getSharedPreferences(_sFile, Context.MODE_PRIVATE);
            return sp.getString(_sKey,defValue);
        }
        return defValue;
    }
}
