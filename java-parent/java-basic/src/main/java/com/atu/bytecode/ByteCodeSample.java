package com.atu.bytecode;
/*
使用java命令运行class文件提示“错误：找不到或无法加载主类“的问题分析
https://www.cnblogs.com/guohu/p/11101285.html
 */
public class ByteCodeSample {
    public static void main(String[] args) {
        int i=1,j=5;
        i++;
        ++j;
        System.out.println(i);
        System.out.println(j);
    }
}
