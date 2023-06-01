package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 * <p>
 * 本类是对Jackson工具库的进一步封装
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
public class MyJsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 将java对象转换成Json字符串
     *
     * @param t   Java对象
     * @param <T> bean数据类型
     * @return Json 字符串
     */
    public static <T> String toJsonStr(T t) {
        String json;
        try {
            json = objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON string");
        }
        return json;
    }


    /**
     * 将json字符串转换成对应的java对象
     *
     * @param json  json字符串
     * @param clazz 目标Java类型
     * @return Java 对象
     */
    public static <T> T getValue(String json, Class<T> clazz) {
        T bean;
        try {
            bean = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to object");
        }
        return bean;
    }


    /**
     * 根据目标Java类型的 类名 从json字符串中获取对应value，并转成对应Java对象
     *
     * @param json  json字符串
     * @param clazz 目标Java类型
     * @return Java 对象
     */
    public static <T> T getValueByKey(String json, Class<T> clazz) {
        return getValueByKey(json, clazz.getSimpleName(), clazz);
    }

    /**
     * 根据key值从json字符串中获取对应value，并转成对应Java对象
     *
     * @param json  json字符串
     * @param key   json中的键
     * @param clazz 目标Java类型
     * @return Java 对象
     */
    public static <T> T getValueByKey(String json, String key, Class<T> clazz) {
        T bean = null;
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode node = root.get(key);
            if (node != null) {
                bean = objectMapper.treeToValue(node, clazz);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return bean;
    }


}
