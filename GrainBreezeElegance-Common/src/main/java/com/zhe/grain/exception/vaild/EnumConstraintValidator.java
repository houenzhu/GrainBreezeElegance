package com.zhe.grain.exception.vaild;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class EnumConstraintValidator implements ConstraintValidator<EnumValidate, Integer> {
    private final HashSet<Integer> set = new HashSet<>();
    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            set.add(value);
            System.out.println("注入指定的value = " + value);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
