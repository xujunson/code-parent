package com.atu.senior.Agent.cglib;

import com.atu.senior.Agent.staticAgent.RealStar;
import com.atu.senior.Agent.staticAgent.Star;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/24 10:36
 * @Description : 测试客户端
 */
public class Client3 {
    /**
     * 测试Cglib动态代理结果
     *
     * @param args args
     */
    public static void main(String[] args) {
        Star realStar = new RealStar();
        Star proxy = (Star) new CglibProxyHandler().getProxyInstance(realStar);

        proxy.sing();
    }
}
