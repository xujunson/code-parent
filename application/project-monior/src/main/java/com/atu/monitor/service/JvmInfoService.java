package com.atu.monitor.service;

import com.alibaba.fastjson.JSON;
import com.atu.monitor.domian.JvmGcInfo;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Tom
 * @create: 2023-02-13 16:32
 * @Description: jvm信息
 */
public class JvmInfoService {
    public static void main(String[] args) {
        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();

        // 堆内存
        MemoryUsage heapMen = mbean.getHeapMemoryUsage();

        // 返回 Java 虚拟机最初从操作系统请求用于内存管理的内存量（以字节为单位）。
        System.out.println(heapMen.getInit());

        //返回可以用于内存管理的最大内存量（以字节为单位）。
        System.out.println(heapMen.getMax());

        // 返回已使用的内存量（以字节为单位）。
        System.out.println(heapMen.getUsed());

        // 返回已提交给 Java 虚拟机使用的内存量（以字节为单位）。
        System.out.println(heapMen.getCommitted());

        // 非堆内存
        heapMen = mbean.getNonHeapMemoryUsage();
        System.out.println(heapMen.getInit());
        System.out.println(heapMen.getMax());
        System.out.println(heapMen.getUsed());
        System.out.println(heapMen.getCommitted());

        // 取GC信息
        List<JvmGcInfo> gcList = new ArrayList<JvmGcInfo>();
        List<GarbageCollectorMXBean> gcMXbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : gcMXbeans) {
            String name = garbageCollectorMXBean.getName();
            JvmGcInfo gcInfo = new JvmGcInfo();
            gcInfo.setGcName(name);
            gcInfo.setGcTotalCount(garbageCollectorMXBean.getCollectionCount());
            gcInfo.setGcTotalTime(garbageCollectorMXBean.getCollectionTime());
            gcList.add(gcInfo);
        }
        System.out.println(JSON.toJSON(gcList));


        // 取得类加载信息
        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        // 返回当前加载到 Java 虚拟机中的类的数量。
        System.out.println(classBean.getLoadedClassCount());
        // 返回自 Java 虚拟机开始执行到目前已经加载的类的总数。
        System.out.println(classBean.getTotalLoadedClassCount());
        // 返回自 Java 虚拟机开始执行到目前已经卸载的类的总数。
        System.out.println(classBean.getUnloadedClassCount());
    }
}
