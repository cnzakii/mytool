package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import constant.TimeConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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
     * ObjectMapper对象
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(TimeConstants.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(TimeConstants.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TimeConstants.DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(TimeConstants.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(TimeConstants.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TimeConstants.DEFAULT_TIME_FORMAT)));

        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
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
