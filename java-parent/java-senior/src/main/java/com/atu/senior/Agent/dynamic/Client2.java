package com.atu.senior.Agent.dynamic;

import com.atu.senior.Agent.staticAgent.RealStar;
import com.atu.senior.Agent.staticAgent.Star;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/24 10:24
 * @Description :
 */
public class Client2 {
    /**
     * 测试JDK动态代理结果
     *
     * @param args args
     */
    public static void main(String[] args) {
        Star realStar = new RealStar();
        // 创建一个代理对象实例
        Star proxy = (Star) new JdkProxyHandler(realStar).getProxyInstance();

        proxy.sing();
    }
}
