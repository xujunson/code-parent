package com.atu.future;


import java.util.concurrent.*;

/**
 * @author: Tom
 * @date: 2020-03-24 16:19
 * @description: 演示FutureTask的用法
 */
public class FutureTaskDemo {
    public static void main(String[] args) {
        //第一步:建立一个任务
        Task task = new Task();

        //第二步：创建FutureTask，在构造函数中传入 task
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);

        //第三步：放到线程中去执行
        //new Thread(integerFutureTask).start();

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(integerFutureTask);

        try {
            System.out.println("task运行结果：" + integerFutureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程正在计算");
        Thread.sleep(3000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}