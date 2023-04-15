package com.atu.monitor.domian;

/**
 * @author: Tom
 * @create: 2023-02-13 16:33
 * @Description: jvmgcinfo
 */
public class JvmGcInfo {
    /**
     * GC名称
     */
    private String gcName;
    /**
     * GC总次数
     */
    private long gcTotalCount;
    /**
     * GC总时间
     */
    private long gcTotalTime;

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public long getGcTotalCount() {
        return gcTotalCount;
    }

    public void setGcTotalCount(long gcTotalCount) {
        this.gcTotalCount = gcTotalCount;
    }

    public long getGcTotalTime() {
        return gcTotalTime;
    }

    public void setGcTotalTime(long gcTotalTime) {
        this.gcTotalTime = gcTotalTime;
    }

}
