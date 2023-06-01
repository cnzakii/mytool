package validation.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import validation.annotation.ZeroOrOne;

/**
 * 注解ZeroOrOne 的Validator<br>
 * 用于校验int类型的数值仅能为0或者1
 *
 * @author Zaki
 * @version 2.0
 * @since 2023-05-15
 **/
public class ZeroOrOneValidator implements ConstraintValidator<ZeroOrOne, Integer> {


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && (value == 0 || value == 1);
    }
}
