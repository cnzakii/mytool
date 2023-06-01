package validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import validation.validator.FixedValuesValidator;

import java.lang.annotation.*;

/**
 * 校验int数值
 * <p>
 * 校验int类型的数值是否属于values数组
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
@Documented
@Constraint(validatedBy = {FixedValuesValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedValues {
    int[] values();

    String message() default "Invalid value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
