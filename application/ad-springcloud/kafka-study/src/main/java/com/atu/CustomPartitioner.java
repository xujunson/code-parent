package com.atu;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-04-01 15:32
 * @description: 自定义分区分配器
 */
public class CustomPartitioner implements Partitioner {

    /***
     * 决定消息将会被写入哪个分区
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key,
                         byte[] keyBytes, Object value,
                         byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numPartitions = partitionInfos.size();
        //要求key必填
        if (null == keyBytes || !(key instanceof String)) {
            throw new InvalidRecordException("kafka message must have key");
        }

        //如果分区数只有1个 那么没有可选择性
        if (numPartitions == 1) {
            return 0;
        }
        //如果key是name，方到最后一个分区
        if (key.equals("name")) {
            return numPartitions - 1;
        }

        //kafka默认分配器策略：Utils.murmur2(keyBytes)——根据key获取hash值，对分区数取余
        return Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1);
    }

    /**
     * 关闭分配
     */
    @Override
    public void close() {

    }

    /**
     * 对分区分配器进行配置
     *
     * @param map
     */
    @Override
    public void configure(Map<String, ?> map) {

    }
}
