package com.atu.extensible.task;


public interface QuartzScheduleTask {
    /**
     * 该方法的实现，表示某个任务的具体工作内容
     */
    void exec();
}
