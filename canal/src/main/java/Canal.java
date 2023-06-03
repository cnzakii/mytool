import lombok.extern.slf4j.Slf4j;
import util.MyCanalUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * canal执行程序
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-03
 **/
@Slf4j
public class Canal {

    public static void exec(String json) {
        log.info("Receiving messages from Canal transmission===》{}", json);
        // 获取表名
        String table = MyCanalUtils.getTable(json);

        // 查找带有@CanalTable("table")注解的Bean对象
        Object bean = MyCanalUtils.getCanalTableBean(table);
        if (Objects.isNull(bean)) {
            return;
        }

        // 获取操作类型-INSERT UPDATE DELETE
        String type = MyCanalUtils.getType(json);

        // 根据bean和type 获取对应方法
        Method method = Optional.ofNullable(MyCanalUtils.getMethodByTypeName(bean, type))
                .orElseThrow(() -> new RuntimeException("Cannot find the corresponding method"));

        // 获取 bean 对象的父类泛型类型
        Class<?> methodType = Optional.ofNullable(MyCanalUtils.getGenericSuperClassType(bean))
                .orElseThrow(() -> new RuntimeException("Failed to get the generic type"));

        // 获取变化的数据集合
        List<?> changeData = Optional.ofNullable(MyCanalUtils.getNewDataList(json, methodType))
                .orElseThrow(() -> new RuntimeException("Change data list is empty"));


        try {
            // 如果是更新操作，就多传入一个参数
            if (Objects.equals(type, "UPDATE")) {
                // 获取旧数据集合
                List<?> oldData = MyCanalUtils.getOldDataList(json, methodType);
                // 执行更新操作
                method.invoke(bean, oldData, changeData);
                return;
            }

            // 插入和删除 仅传入一个参数
            method.invoke(bean, changeData);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


}
