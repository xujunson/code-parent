package com.atu.senior.Agent.staticAgent;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/24 10:21
 * @Description :
 */
public class Client {
    /**
     * 测试静态代理结果
     *
     * @param args args
     */
    public static void main(String[] args) {
        Star realStar = new RealStar();
        Star proxy = new ProxyStar(realStar);

        proxy.sing();
    }
}
