package com.yeapoo.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 * 
 * @author Simon
 *
 */
public class MapUtil {

    private MapUtil() {}

    /**
     * 获取String类型的value
     * 
     * @param map 要从中取数据的map
     * @param key map的key
     * @return String类型的value
     */
    public static String get(Map<String, Object> map, String key) {
        return get(map, key, String.class);
    }

    /**
     * 获取String类型的value, 并将其从map中移除
     * 
     * @param map 要从中取数据的map
     * @param key map的key
     * @return String类型的value
     */
    public static String getAndRemove(Map<String, Object> map, String key) {
        return getAndRemove(map, key, String.class);
    }

    /**
     * 获取指定类型的value
     * 
     * @param map 要从中取数据的map
     * @param key map的key
     * @param clazz 指定的返回类型
     * @return 指定类型的value
     */
    public static <T> T get(Map<String, Object> map, String key, Class<T> clazz) {
        Object tmp = map.get(key);
        return null != tmp ? clazz.cast(tmp) : null;
    }

    /**
     * 获取指定类型的value, 并将其从map中移除
     * 
     * @param map 要从中取数据的map
     * @param key map的key
     * @param clazz 指定的返回类型
     * @return 指定类型的value
     */
    public static <T> T getAndRemove(Map<String, Object> map, String key, Class<T> clazz) {
        Object tmp = map.remove(key);
        return null != tmp ? clazz.cast(tmp) : null;
    }

    /**
     * 将map中的数据扁平化
     * 
     * @param mapList 要进行扁平化处理的map
     * @param key 要进行提取的key
     * @return 扁平化后的List<String>
     */
    public static List<String> flat(List<Map<String, Object>> mapList, String key) {
        List<String> list = new ArrayList<String>();
        for (Map<String, Object> map : mapList) {
            list.add(MapUtil.get(map, key));
        }
        return list;
    }
}
