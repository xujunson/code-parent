package com.atu.senior.Agent.staticAgent;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/24 10:21
 * @Description : 明星的静态代理类
 */
public class ProxyStar implements Star {

    /**
     * 接收真实的明星对象
     */
    private Star star;

    /**
     * 通过构造方法传进来真实的明星对象
     *
     * @param star star
     */
    public ProxyStar(Star star) {
        this.star = star;
    }

    @Override
    public void sing() {
        System.out.println("代理先进行谈判……");
        // 唱歌只能明星自己唱
        this.star.sing();
        System.out.println("演出完代理去收钱……");
    }

}