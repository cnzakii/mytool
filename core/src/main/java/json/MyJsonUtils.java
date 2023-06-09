package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


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
public class MyJsonUtils {

    /**
     * ObjectMapper 单例对象
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 属性命名策略设置为将驼峰式命名转换为下划线分隔的命名
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        // 注册时间模块, 支持支持jsr310, 即新的时间类(java.time包下的时间类)
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 获取 objectMapper对象
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
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

    /**
     * 根据key值从json字符串中获取对应value，并转成对应Java对象
     *
     * @param json json字符串
     * @param key  json中的键
     * @param type 类型引用对象，用于指定要解析的目标类型
     * @return Java 对象
     */
    public static <T> T getValueByKey(String json, String key, JavaType type) {
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
