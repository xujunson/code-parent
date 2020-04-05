package com.atu.util;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-04-05 16:39
 * @description: 字段值到实体类的映射工具类
 */
public class MapToEntity {
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> targetClass) throws IllegalAccessException, InstantiationException {
        //存储父类
        Class superClass;
        //存储父类中的属性
        Field[] fields;

        //new 一个实例对象
        T target = targetClass.newInstance();

        //存储 targetClass 的所有Field
        List<Field> targetFieldList = new LinkedList<>();

        //先把目标类赋值给父类
        superClass = targetClass;

        //将targetClass所有父类的属性 都保存到 targetFieldList里
        while (superClass != null && superClass != Object.class) {
            //getDeclaredFields只能获取到当前类的参数，但是没办法获取父类参数
            fields = superClass.getDeclaredFields();
            //存储当前类的所有属性字段
            targetFieldList.addAll(Arrays.asList(fields));

            //获取父类
            superClass = superClass.getSuperclass();
        }

        for (Field targetField : targetFieldList) {
            //map中每一个对象都是一个entry
            for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
                if (targetField.getName().equals(mapEntry.getKey())) {
                    //显示保存权限
                    boolean targetFlag = targetField.isAccessible();

                    //赋予修改权限
                    targetField.setAccessible(true);
                    //赋值
                    targetField.set(target, mapEntry.getValue() instanceof BigInteger ?
                            ((BigInteger) mapEntry.getValue()).longValue() : mapEntry.getValue());

                    //恢复原权限
                    targetField.setAccessible(targetFlag);
                    break;
                }
            }
        }
        return target;
    }
}
