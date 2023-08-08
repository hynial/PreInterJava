package com.hynial.preinter.multithread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadBean {

    public static void main(String[] args) {

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threads = threadMXBean.dumpAllThreads(false, false);

        for(ThreadInfo threadInfo : threads) {
            System.out.println(threadInfo.getThreadId() + ", " + threadInfo.getThreadName());
        }
    }
}
