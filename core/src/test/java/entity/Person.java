package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 实体类： 人
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private String name;

    private int age;

    private String[] address;
}
