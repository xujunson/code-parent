package com.atu.index.adunit;

/**
 * @author: Tom
 * @date: 2020-03-31 10:43
 * @description: 推广单元常量定义
 */
public class AdUnitConstants {
    //二进制编排——目的 可以使用位或运算，位与运算 加快索引速度
    public static class POSITION_TYPE {
        //开屏广告
        public static final int KAIPING = 1;
        //贴片广告
        public static final int TIEPIAN = 2;
        //视频播放中间的广告
        public static final int TIEPIAN_MIDDLE = 4;
        //暂停时播放的广告
        public static final int TIEPIAN_PAUSE = 8;
        //视频播放完之后的广告
        public static final int TIEPIAN_POST = 16;
    }
}
