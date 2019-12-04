package com.atu.senior.ws.TheService;

/**
 * @author ：Tom
 * @date ：Created in 2019/9/9 14:00
 * @description：WebService
 */
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class ServiceHello {

    public String getValue(String name){

        return "hello:"+name;
    }
    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:9095/service/ServiceHello", new ServiceHello());
        System.out.println("Publish Success!!! ");
    }

}