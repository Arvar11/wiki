package com.panda.wiki.util;

import org.springframework.stereotype.Component;


public class SnowFlake {
    
    // 起始时间戳（可以设置为项目启动时间）
    private final long twepoch = 1704067200000L; // 2024-01-01 00:00:00
    
    // 各部分位数
    private final long datacenterIdBits = 5L;   // 数据中心5位
    private final long workerIdBits = 5L;       // 机器ID5位  
    private final long sequenceBits = 12L;      // 序列号12位
    
    // 最大值
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);  // 31
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);          // 31
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);         // 4095
    
    // 移位
    private final long workerIdShift = sequenceBits;                       // 12
    private final long datacenterIdShift = sequenceBits + workerIdBits;    // 17
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; // 22
    
    private long datacenterId;
    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    
    // 构造函数，传入数据中心ID和机器ID
    public SnowFlake(long datacenterId, long workerId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("数据中心ID范围 0-" + maxDatacenterId);
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("机器ID范围 0-" + maxWorkerId);
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }
    
    // 生成下一个ID
    public synchronized long nextId() {
        long timestamp = timeGen();
        
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("系统时钟回退，拒绝生成ID");
        }
        
        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        
        // 上次生成ID的时间截
        lastTimestamp = timestamp;
        
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }
    
    // 阻塞到下一个毫秒，直到获得新的时间戳
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    
    // 返回当前时间（毫秒）
    private long timeGen() {
        return System.currentTimeMillis();
    }
}