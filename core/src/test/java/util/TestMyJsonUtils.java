package util;

import entity.Person;
import json.MyJsonUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * MyJsonUtils 测试类
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/


public class TestMyJsonUtils {

    private static final String json = "{\"name\":\"myTool\",\"age\":1,\"address\":[\"中国\",\"福建\"]}";

    private static final String beanJson = "{\"Person\":{\"name\":\"myTool\",\"age\":1,\"address\":[\"中国\",\"福建\"]}}";


    @Test
    public void testToJsonStr() {
        Person person = new Person("myTool", 1, new String[]{"中国", "福建"});

        String jsonStr = MyJsonUtils.getJsonStr(person);
        System.out.println(jsonStr);
    }

    @Test
    public void testGetValue() {
        Person bean = MyJsonUtils.getValue(json, Person.class);
        System.out.println(bean);
    }


    @Test
    public void testGetValueByKey() {
        String name = MyJsonUtils.getValueByKey(json, "name", String.class);
        System.out.println(name);

        Integer age = MyJsonUtils.getValueByKey(json, "age", Integer.class);
        System.out.println(age);

        String[] address = MyJsonUtils.getValueByKey(json, "address", String[].class);
        System.out.println(Arrays.toString(address));

        Person person = MyJsonUtils.getValueByKey(beanJson, "Person", Person.class);
        System.out.println(person);

        Person person2 = MyJsonUtils.getValueByKey(beanJson, Person.class);
        System.out.println(person2);


        String datetime = "{\"datetime\":\"2023-06-05 15:17:11.117\"}";
        LocalDateTime value = MyJsonUtils.getValueByKey(datetime, "datetime", LocalDateTime.class);
        System.out.println(value);


    }

}
