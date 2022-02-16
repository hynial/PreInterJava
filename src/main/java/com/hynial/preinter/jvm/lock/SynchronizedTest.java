package com.hynial.preinter.jvm.lock;

// Run with -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:TieredStopAtLevel=1
//-XX:+UseBiasedLocking
//调用 Object.hashCode() 会关闭该对象的偏向锁
//Hotspot虚拟机在开机启动后有个延迟（4s），经过延迟后才会对每个创建的对象开启偏向锁。
// 我们可以通过设置下面的参数来修改这个延迟，或者直接sleep一段时间-XX:BiasedLockingStartupDelay=0
public class SynchronizedTest {
    static Lock lock = new Lock();
    static int counter = 0;

    public static void foo() {
        synchronized (lock) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        lock.hashCode(); // Step 2
//        System.identityHashCode(lock); // Step 4
        for (int i = 0; i < 1_000_000; i++) {
            foo();
        }

        System.out.println(counter);
    }

    static class Lock {
//        @Override
//        public int hashCode() {
//            return 0;
//        }  // Step 3
    }
}