package com.chervonnaya.paymentservice.service;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class RoundRobinPartitioner implements Partitioner {

    private int lastPartition = -1;

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int numPartitions = cluster.partitionCountForTopic(topic);

        if (keyBytes == null) {
            synchronized (this) {
                lastPartition = (lastPartition + 1) % numPartitions;
                return lastPartition;
            }
        } else {
            return Math.abs(key.hashCode()) % numPartitions;
        }
    }

    @Override
    public void close() {

    }

}
