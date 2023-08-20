package util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;


/**
 * json工具类
 * <p>
 * 本类是对Jackson的进一步封装
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
@Getter
public class MyJsonUtils {

    /**
     * ObjectMapper 单例对象
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //解决时间格式等一系列问题
        objectMapper.findAndRegisterModules();
    }

    /**
     * 将java对象转换成Json字符串
     *
     * @param t   Java对象
     * @param <T> bean数据类型
     * @return Json 字符串
     */
    public static <T> String getJsonStr(T t) {
        String json;
        try {
            json = objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON string");
        }
        return json;
    }

    /**
     * 解析Json字符串，反序列化为Map集合
     *
     * @param json   json字符串
     * @param kClazz key的Java类型
     * @param vClazz value的Java类型
     * @return map
     */
    public static <K, V> Map<K, V> getMap(String json, Class<K> kClazz, Class<V> vClazz) {
        if (StringUtils.isBlank(json) || kClazz == null || vClazz == null) {
            return Collections.emptyMap();
        }

        Map<K, V> map;
        try {
            map = objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(Map.class, kClazz, vClazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }


        return map;
    }


    /**
     * 将json字符串转换成对应的java对象
     *
     * @param json  json字符串
     * @param clazz 目标Java类型
     * @return Java 对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        T bean;
        try {
            bean = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to object, The reason is " + e.getLocalizedMessage());
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
    public static <T> T parseObjectByKey(String json, Class<T> clazz) {
        return parseObjectByKey(json, clazz.getSimpleName(), clazz);
    }

    /**
     * 根据key值从json字符串中获取对应value，并转成对应Java对象
     *
     * @param json  json字符串
     * @param key   json中的键
     * @param clazz 目标Java类型
     * @return Java 对象
     */
    public static <T> T parseObjectByKey(String json, String key, Class<T> clazz) {
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

    /**
     * 根据key值从json字符串中获取对应value，并转成对应Java对象
     *
     * @param json json字符串
     * @param key  json中的键
     * @param type 类型引用对象，用于指定要解析的目标类型,主要针对List集合等
     * @return Java 对象
     */
    public static <T> T parseObjectByKey(String json, String key, JavaType type) {
        T bean = null;
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode node = root.get(key);
            if (node != null) {
                bean = objectMapper.readValue(node.traverse(), type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return bean;
    }
}
