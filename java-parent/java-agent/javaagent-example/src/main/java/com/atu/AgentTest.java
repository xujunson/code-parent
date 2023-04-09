package com.atu;

/**
 * @author: Tom
 * @date: 2023/4/9 17:39
 * @description: TODO
 **/
//-javaagent:E:\projects\code-parent\java-parent\java-agent\javaagent-demo\target\javaagent-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
public class AgentTest {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println("process result: " + process());
            Thread.sleep(5000);
        }
    }

    public static String process() {
        System.out.println("process!");
        return "success";
    }
}
