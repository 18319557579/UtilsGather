package com.example.utilsgather.source_file.assets;

import android.app.Application;

import com.example.utilsgather.context.ApplicationGlobal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件
 */
public class PropertiesUtil {
    private volatile static Properties properties;

    //这里使用延迟初始化，也可以用手动初始化的方式
    private static Properties getProperties() {
        if (properties == null) {
            synchronized (PropertiesUtil.class) {
                if (properties == null) {

                    properties = new Properties();
                    InputStream inputStream = null;
                    try {
                        inputStream = ApplicationGlobal.getInstance().getAssets().open("appConfig.properties");
                        properties.load(inputStream);
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return properties;
    }

    //获得value为String
    public static String getString(String key) {
        return getProperties().getProperty(key);
    }

    //获得value为int类型
    public static int getInt(String key) {
        return Integer.parseInt(getProperties().getProperty(key));
    }
}
