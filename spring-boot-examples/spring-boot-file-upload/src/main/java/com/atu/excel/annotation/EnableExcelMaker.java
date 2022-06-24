package com.atu.excel.annotation;

import java.lang.annotation.*;


/**
 * 开启导入excel功能
 */
@Documented
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EnableExcelMaker {

}
