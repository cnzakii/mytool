package com.cnzakii.util;

import bean.MyBeanUtils;
import collection.MyCollUtils;
import com.cnzakii.annotation.CanalTable;
import com.cnzakii.common.ApplicationContextProvider;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.MyJsonUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Canal的工具类
 * <p>
 * 本类用于<br>
 * 1.处理Canal通过MQ传输过来的Json字符串，从中获取有用信息<br>
 * 2.通过{@link ApplicationContext}获取处理数据的类和相应的处理方法
 * </P>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-02
 **/
public class MyCanalUtils {

    /**
     * 获取旧数据集合
     *
     * @param json  MQ监听到的json字符串
     * @param clazz 目标Java类型
     * @return 旧数据集合
     */
    public static <T> List<T> getOldDataList(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clazz);

        List<T> list = getNewDataList(json, clazz);
        List<T> oldList = MyJsonUtils.getValueByKey(json, "old", type);

        if (MyCollUtils.hasEmpty(list, oldList)) {
            throw new RuntimeException("List is empty");
        }
        if (list.size() != oldList.size()) {
            throw new RuntimeException("List length is not equal");
        }


        IntStream.range(0, list.size())
                .forEach(i -> MyBeanUtils.copyPropertiesIgnoreNull(oldList.get(i), list.get(i)));

        return list;
    }


    /**
     * 获取新数据集合
     *
     * @param json  MQ监听到的json字符串
     * @param clazz 目标Java类型
     * @return 新数据集合
     */
    public static <T> List<T> getNewDataList(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        return MyJsonUtils.getValueByKey(json, "data", type);
    }

    /**
     * 获取表名
     *
     * @param json MQ监听到的json字符串
     * @return 表名
     */
    public static String getTable(String json) {
        return MyJsonUtils.getValueByKey(json, "table", String.class);
    }


    /**
     * 获取操作类型:<br>
     * INSERT/UPDATE/DELETE
     *
     * @param json MQ监听到的json字符串
     * @return INSERT UPDATE DELETE
     */
    public static String getType(String json) {
        return MyJsonUtils.getValueByKey(json, "type", String.class);
    }


    /**
     * 获取 bean 对象的父类泛型类型
     *
     * @param bean 传入的 bean 对象
     * @return 返回参数类型的 Class 对象，如果获取失败返回 null
     */
    public static Class<?> getGenericSuperClassType(Object bean) {
        // 获取 bean 对象的泛型父类 Type 对象
        Type genericSuperclass = bean.getClass().getGenericSuperclass();

        // 判断泛型父类是否为 ParameterizedType 对象
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            // 获取 ParameterizedType 中的实际类型参数
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            // 判断实际类型参数是否存在，并且是否为 Class 对象
            if (typeArguments.length > 0 && typeArguments[0] instanceof Class<?> genericType) {
                return genericType;
            }
        }
        return null;
    }


    /**
     * 根据type获取对应bean对象中的方法
     *
     * @param bean bean对象
     * @param type 类型
     * @return method
     */
    public static Method getMethodByTypeName(Object bean, String type) {
        String methodName = switch (type) {
            case "INSERT" -> "insertList";
            case "UPDATE" -> "updateList";
            case "DELETE" -> "deleteList";
            default -> throw new RuntimeException("type error");
        };

        return Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .findFirst()
                .orElse(null);
    }


    /**
     * 查找带有@CanalTable("table")注解的Bean对象
     *
     * @param table 表名
     * @return bean对象
     */
    public static Object getCanalTableBean(String table) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(CanalTable.class);
        return beans.values().stream()
                .filter(bean -> AnnotationUtils.findAnnotation(bean.getClass(), CanalTable.class) != null)
                .filter(bean -> Objects.requireNonNull(AnnotationUtils.findAnnotation(bean.getClass(), CanalTable.class)).value().equals(table))
                .findFirst()
                .orElse(null);
    }


}
