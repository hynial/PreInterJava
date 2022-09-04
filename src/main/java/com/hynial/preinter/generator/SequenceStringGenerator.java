package com.hynial.preinter.generator;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

public class SequenceStringGenerator {
    private int nodeId;

    public SequenceStringGenerator(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public SequenceStringGenerator() {
        this.nodeId = createNodeId();
    }

    private int createNodeId() {
        int nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for(int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & MAX_NODE_ID;
        return nodeId;
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final long CUSTOM_EPOCH = LocalDateTime.now().minusYears(100).toInstant(ZoneOffset.UTC).toEpochMilli();
    private static final long NODE_BITS = 4;
    private static final int MAX_NODE_ID = (int) (Math.pow(2, NODE_BITS) - 1);
    private static final long SEQUENCE_BITS = 12; // make it big , P UP
    private static final long MAX_SEQUENCE_ID = (long) (Math.pow(2, SEQUENCE_BITS) - 1);

    private volatile long sequence;
    private volatile long lastTimestamp;

    public synchronized String nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("错误的时间戳 - 小于过去的时间戳");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE_ID;
            if (sequence == 0) {
                while(currentTimestamp == lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0; // 每个微秒都从零开始尽管序列还没有走到尽头，会出现不连续的序列 P UP
        }

        lastTimestamp = currentTimestamp;
//        currentTimestamp -= CUSTOM_EPOCH;

        long id = 1 << (NODE_BITS + SEQUENCE_BITS);
        id |= this.nodeId << SEQUENCE_BITS;
        id |= this.sequence;

//        long actualEpoch = currentTimestamp + CUSTOM_EPOCH;

//        Instant instant = Instant.ofEpochMilli(currentTimestamp);
//        return DATE_TIME_FORMATTER.format(instant.atZone(ZoneId.systemDefault())) + id;

        String result = currentTimestamp + "-" + id;
        return result;
//        return getTimestampString(result);
    }


    public String getTimestampString(String id) {
        String[] split = id.split("-");
        return DATE_TIME_FORMATTER.format(Instant.ofEpochMilli(Long.valueOf(split[0])).atZone(ZoneId.systemDefault())) + split[1];
    }
}
