package com.atu.agent;

import java.lang.instrument.Instrumentation;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tom
 * @create: 2023-04-13 21:23
 * @Description:
 */
public class MyAgentJvmStack {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain1(String agentArgs, Instrumentation inst) {
        //System.out.println("嗨！JavaAgent " + agentArgs);

        System.out.println("this is my agent：" + agentArgs);
        /*MyMonitorTransformer monitor = new MyMonitorTransformer();
        inst.addTransformer(monitor);*/

        /*AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.any()) // 拦截任意方法
                    .intercept(MethodDelegation.to(MethodCostTime.class)); // 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.atu.ApiTest")) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);*/

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                JvmStack.printMemoryInfo();
                JvmStack.printGCInfo();
                System.out.println("===================================================================================================");
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);

    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }


   static class JvmStack {
        private static final long MB = 1048576L;

        static void printMemoryInfo() {
            MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
            MemoryUsage headMemory = memory.getHeapMemoryUsage();

            String info = String.format("\ninit: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                    headMemory.getInit() / MB + "MB",
                    headMemory.getMax() / MB + "MB", headMemory.getUsed() / MB + "MB",
                    headMemory.getCommitted() / MB + "MB",
                    headMemory.getUsed() * 100 / headMemory.getCommitted() + "%"

            );

            System.out.print(info);

            MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();

            info = String.format("init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                    nonheadMemory.getInit() / MB + "MB",
                    nonheadMemory.getMax() / MB + "MB", nonheadMemory.getUsed() / MB + "MB",
                    nonheadMemory.getCommitted() / MB + "MB",
                    nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%"

            );
            System.out.println(info);

        }

        static void printGCInfo() {
            List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean garbage : garbages) {
                String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                        garbage.getName(),
                        garbage.getCollectionCount(),
                        garbage.getCollectionTime(),
                        Arrays.deepToString(garbage.getMemoryPoolNames()));
                System.out.println(info);
            }
        }
    }
}