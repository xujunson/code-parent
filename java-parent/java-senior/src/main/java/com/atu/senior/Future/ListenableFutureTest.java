package com.atu.senior.Future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author ：ta0567
 * @date ：Created in 2019/9/23 21:02
 * @description：${description}
 */
public class ListenableFutureTest {
    public static void main(String[] args) {
        testListenFuture();
    }

    public static void testListenFuture() {
        System.out.println("主线程start");
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

        Task task1 = new Task();
        task1.args = "task1";
        Task task2 = new Task();
        task2.args = "task2";
        ListenableFuture<String> future = pool.submit(task1);
        ListenableFuture<String> future2 = pool.submit(task2);

        future2.addListener(() -> System.out.println("addListener 不能带返回值"), pool);

        /**
         * FutureCallBack接口可以对每个任务的成功或失败单独做出响应
         */
        FutureCallback<String> futureCallback = new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("Futures.addCallback 能带返回值：" + result);
            }
            @Override
            public void onFailure(Throwable t) {
                System.out.println("出错,业务回滚或补偿");
            }
        };

        //为任务绑定回调接口
        Futures.addCallback(future, futureCallback, pool);
        System.out.println("主线程end");
    }
}

class Task implements Callable<String> {
    String args;
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        System.out.println("任务：" + args);
        return "dong";
    }
}
