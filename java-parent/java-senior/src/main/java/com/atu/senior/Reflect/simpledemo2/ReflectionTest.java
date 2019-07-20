package com.atu.senior.Reflect.simpledemo2;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 13:01
 * @Description :
 */
public class ReflectionTest {

    @Test
    public void testGetParentField() throws Exception{
        Class c1 = Class.forName("com.atu.senior.Reflect.simpledemo2.Son");
        //获取父类私有属性值
        System.out.println(getFieldValue(c1.newInstance(),"privateField"));
    }

    public static Field getDeclaredField(Object obj, String fieldName) {
        Field field = null;
        Class c = obj.getClass();
        for(; c != Object.class ; c = c.getSuperclass()){
            try {
                field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            }catch (Exception e){
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行c = c.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }
    public static Object getFieldValue(Object object,String fieldName) throws Exception{
        Field field = getDeclaredField(object,fieldName);

        return field.get(object);
    }
}
