package com.atu.excel.annotation;

import java.lang.annotation.*;

/**
 * 指定生成的excel文件包含的属性和属性名还有位置,已经是否合并,顺序
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelField {

    public int position();

    public String name() default "";

    public int width() default -1;

    public boolean merge() default false;
}
