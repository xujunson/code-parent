package com.atu.excel.annotation;

import java.lang.annotation.*;


/**
 * 用于设置导入表格的头部标题
 */
@Documented
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
    public String value();
}
