package util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Canal的工具类
 * <p>
 * 本类用于处理Canal通过MQ传输过来的Json字符串，从中获取有用信息
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

        List<T> list = getNewDataList(json, clazz);
        List<T> oldList = MyJsonUtils.getValueByKey(json, "old", new TypeReference<>() {
        });

        if (MyCollUtils.hasEmpty(list,oldList)) {
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
        if (!clazz.isArray()) {
            throw new RuntimeException("Class Type Must Be Array");
        }
        return MyJsonUtils.getValueByKey(json, "data", new TypeReference<>() {
        });
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
}
