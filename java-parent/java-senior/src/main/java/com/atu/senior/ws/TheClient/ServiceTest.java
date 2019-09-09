package com.atu.senior.ws.TheClient;

/**
 * @author ：ta0567
 * @date ：Created in 2019/9/9 14:08
 * @description：ws test
 */
public class ServiceTest {
    public static void main(String[] args){
        ServiceHello hello = new ServiceHelloService().getServiceHelloPort();
        String name = hello.getValue("atu");
        System.out.println(name);
    }
}
