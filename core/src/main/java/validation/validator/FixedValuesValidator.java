package validation.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import validation.annotation.FixedValues;

import java.util.Arrays;

/**
 * 注解FixedValues的Validator
 * <p>
 * 校验int类型的数值是否属于values数组
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-05-15
 **/
public class FixedValuesValidator implements ConstraintValidator<FixedValues, Integer> {
    private int[] allowedValues;

    @Override
    public void initialize(FixedValues constraintAnnotation) {
        allowedValues = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && Arrays.stream(allowedValues).anyMatch(element -> element == value);
    }
}
