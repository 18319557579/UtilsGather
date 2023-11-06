package com.example.utilsgather.context;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;

public class ApplicationGlobal {
    private ApplicationGlobal(){}

    private volatile static Application instance;

    //使用双重保护锁来实现单例
    public static Application getInstance() {
        if (instance == null) {
            synchronized (ApplicationGlobal.class) {
                if (instance == null) {
                    try {
                        instance = (Application) (Class.forName("android.app.ActivityThread"))
                                .getMethod("currentApplication")
                                .invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }
}
