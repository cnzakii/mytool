package validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import validation.validator.ZeroOrOneValidator;

import java.lang.annotation.*;

/**
 * 校验int数值只能是0或者1
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
@Documented
@Constraint(validatedBy = {ZeroOrOneValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZeroOrOne {
    String message() default "Value must be 0 or 1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}