package com.example.utilsgather.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {
    /**
     * 根据value找到map中对应的key（找到的第一个）
     */
    public static <K, V> K getKey(Map<K, V> map, V value) {
        Set<Map.Entry<K, V>> entrySet = map.entrySet();
        Iterator<Map.Entry<K, V>> iterator = entrySet.iterator();
        K key = null;
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            if (entry.getValue().equals(value)) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }
}
