package org.cubixmc.server.util;

import java.util.Map;

public class ListUtil {

    public static <K, V> void putAll(Map<K, V> map, V value, K... keys) {
        for(K key : keys) {
            map.put(key, value);
        }
    }
}
