package com.atu.excel.annotation;

import java.lang.annotation.*;

/**
 * 映射注解类,将sheet名称解析到实体中
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MappingSheetName {

}
