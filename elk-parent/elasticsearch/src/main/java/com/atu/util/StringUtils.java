package com.atu.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 继承自Spring util的工具类，减少jar依赖
 *
 */
public class StringUtils extends org.springframework.util.StringUtils {
    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * <p><pre class="code">
     * StringUtils.isBlank(null) = true
     * StringUtils.isBlank("") = true
     * StringUtils.isBlank(" ") = true
     * StringUtils.isBlank("12345") = false
     * StringUtils.isBlank(" 12345 ") = false
     * </pre>
     *
     * @param cs the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean isBlank(final CharSequence cs) {
        return !StringUtils.isNotBlank(cs);
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace
     * @see Character#isWhitespace
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.hasText(cs);
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll  the {@code Collection} to convert
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String join(Collection<?> coll, String delim) {
        return StringUtils.collectionToDelimitedString(coll, delim);
    }

    /**
     * Convert a {@code String} array into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param arr   the array to display
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String join(Object[] arr, String delim) {
        return StringUtils.arrayToDelimitedString(arr, delim);
    }

    /**
     * 生成uuid
     *
     * @return UUID
     */
    public static String getUUId() {
        return UUID.randomUUID().toString();
    }

    /**
     * list转字符串去中括号
     *
     * @param list
     * @return
     */
    public static String strip(List list) {
        String target = org.apache.commons.lang.StringUtils.strip(list.toString(),
                "[]");
        return target;
    }

    public static boolean isNotNull(Object obj) {
        boolean b = true;
        if (obj instanceof String) {
            if (((String) obj).trim().equals("")) {
                b = false;
            }
        } else if (obj == null) {
            b = false;
        }
        return b;
    }

    public static boolean isAllFieldNull(Object obj) throws Exception {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        System.out.println(obj);
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            if (f.toString().contains("serialVersionUID")) {
                continue;
            } else {
                f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
                Object val = f.get(obj);// 得到此属性的值
                if (!isNullOrEmpty(val)) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    public static boolean isNull(Object obj) {
        boolean b = true;
        if (obj instanceof String) {
            String value = (String) obj;
            if (!value.equals("")) {
                b = false;
            }
        } else {
            if (obj != null) {
                b = false;
            }
        }
        return b;
    }

    /**
     * 是否是数字
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个基本类型对象是否相等
     */
    public static boolean isEquals(Object a,Object b){
        if((a instanceof String) && (b instanceof String)){
            return a.equals(b);
        }else if(a==b){
            return true;
        }
        return false;
    }
    /**
     * 转化为单引号字符
     * @param str
     * @return
     */
    public static String convertSingleStr(String str) {
    	String newStr="";
		String strArr[]=str.split(",");
		for(String s:strArr) {
			newStr+="'"+s+"',";
		}
		newStr=newStr.substring(0,newStr.lastIndexOf(","));
		return newStr;
    }

    /**
     * s1中是否包含s2字符串
     * @param s1 格式如a,b
     * @param s2
     * @return
     */
    public static boolean contains(String s1,String s2){
        boolean b=false;
        String []s=s1.split(",");
        for(int i=0;i<s.length;i++){
            if(s[i].equals(s2)){
                b=true;
                break;
            }
        }
        return b;
    }

    /**
     * s1中是否包含s2字符串
     * @param s1
     * @param s2
     * @return
     */
    public static boolean contains(Collection<String> s1,String s2){
        boolean b=false;
        Iterator<String>iterator=s1.iterator();
        while (iterator.hasNext()){
            if(iterator.next().equals(s2)){
                b=true;
                break;
            }
        }
        return b;
    }

    /**
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String formatJson(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(addIndentBlank(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    char d = jsonStr.charAt(i-1);
                    if(d == '"' || d == ']'){
                        jsonForMatStr.append(c+"\n");
                    } else {
                        jsonForMatStr.append(c);
                    }
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(addIndentBlank(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }
    /**
     * 添加空格
     * @param level
     * @return
     */
    private static String addIndentBlank(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public static void main(String[] args){
        Set<Integer>list1=new HashSet<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        Set<Integer>list2=new HashSet<>();
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list1.retainAll(list2);
        System.out.println(list1.toString());
    }
}
