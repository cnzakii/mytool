package util;

import com.fasterxml.jackson.core.type.TypeReference;
import entity.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

        List<String> addressList = MyJsonUtils.getValueByKey(json, "address", new TypeReference<>() {
        });
        System.out.println(addressList);

        Person person = MyJsonUtils.getValueByKey(beanJson, "Person", Person.class);
        System.out.println(person);

        Person person2 = MyJsonUtils.getValueByKey(beanJson, Person.class);
        System.out.println(person2);


    }

}
