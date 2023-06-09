package bean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * Bean工具类
 * <p>
 * 本类是对Spring{@link BeanUtils}的进一步封装
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
public class MyBeanUtils {


    /**
     * 复制对象属性，忽略为null的属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    /**
     * 获取对象为null的属性名
     *
     * @param source 源对象
     * @return 属性名集合
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        return Arrays.stream(pds)
                .map(FeatureDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

}
