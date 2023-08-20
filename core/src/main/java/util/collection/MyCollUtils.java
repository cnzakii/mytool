package util.collection;

import java.util.Arrays;
import java.util.Collection;

/**
 * 和集合有关的工具类
 * <p>
 * 本类针对{@link Collection}及其实现类进行封装
 * </P>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-02
 **/
public class MyCollUtils {

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 检查传入的所有集合是否存在空集合
     *
     * @param collections Collection<?>集合
     * @return 只要有一个为空，则返回ture
     */
    public static boolean hasEmpty(Collection<?>... collections) {
        return Arrays.stream(collections).anyMatch(MyCollUtils::isEmpty);
    }
}
