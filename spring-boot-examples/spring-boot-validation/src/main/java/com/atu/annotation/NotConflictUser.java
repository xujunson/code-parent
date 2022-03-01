package com.atu.annotation;

import com.atu.validation.UserValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: Tom
 * @Date: 2022/3/1 10:49
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = UserValidation.NotConflictUserValidator.class)
public @interface NotConflictUser {
    String message() default "用户名称、邮箱、手机号码与现存用户产生重复";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
