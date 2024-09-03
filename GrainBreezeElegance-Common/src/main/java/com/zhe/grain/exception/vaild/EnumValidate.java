package com.zhe.grain.exception.vaild;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Documented
@Constraint(validatedBy = EnumConstraintValidator.class)
public @interface EnumValidate {
    String message() default "{com.zhe.grain.exception.valid.EnumValidate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    int[] values() default {};
    String regexp() default "";
}
