package com.atu.excel.annotation;

import java.lang.annotation.*;

/**
 * 获取当前数据在页面中的行数，放入实体中对应属性
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LineNumber {

}
