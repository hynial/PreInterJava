package com.hynial.preinter;

import com.hynial.preinter.generator.SequenceGenerator;
import com.hynial.preinter.generator.SequenceStringGenerator;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceGeneratorTest {

    private ConcurrentHashMap<Long, Object> concurrentHashMap = new ConcurrentHashMap<>();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    public void generate() throws ExecutionException, InterruptedException {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
//        System.out.println(sequenceGenerator.nextId());

//        int c = 100000;
//        long start = System.nanoTime();
//        for(int i = 0; i < c; i++){
//            sequenceGenerator.nextId();
//        }
//
//        long delta = System.nanoTime() - start;
//        System.out.println(delta/1000000.0);

        CountDownLatch countDownLatch = new CountDownLatch(10);
        long s = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            SequenceGenerator sequenceGenerator1 = new SequenceGenerator(i);
            int f = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        int j = 100000;
                        while (j > 0) {
                            long id = sequenceGenerator1.nextId();
                            if (concurrentHashMap.putIfAbsent(id, new AtomicLong()) != null) {
                                throw new IllegalStateException("repeat!");
                            }
//                            concurrentHashMap.put(id, Thread.currentThread().getName());
                            j--;
                        }
                    } catch (Throwable throwable) {
                        System.out.println(throwable);
                    } finally {
//                        System.out.println(f);
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        long delta = System.nanoTime() - s;
        System.out.println(String.format("Size:%d, TotalConsumeMilliSeconds:%.4f", concurrentHashMap.keySet().size(), (delta / 1000000.0)));

        concurrentHashMap.keySet().stream().sorted().limit(100).forEach(id -> System.out.println(id));
    }

    @Test
    public void atomic() {
        AtomicLong atomicLong = new AtomicLong();
        int c = 100000;
        long start = System.nanoTime();
        for (int i = 0; i < c; i++) {
            atomicLong.incrementAndGet();
        }

        long delta = System.nanoTime() - start;
        System.out.println(delta / 1000000.0);
    }

    @Test
    public void generateLocal() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(sequenceGenerator.nextId());
        }
    }


    private ConcurrentHashMap<String, AtomicLong> stringConcurrentHashMap = new ConcurrentHashMap<>();

    @Test
    public void generateStringSequenceWithTime() {
        SequenceStringGenerator sequenceStringGenerator = new SequenceStringGenerator();

        System.out.println(sequenceStringGenerator.nextId());

        long s = System.nanoTime();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            SequenceStringGenerator sequenceStringGenerator1 = new SequenceStringGenerator(i + 1);

            executorService.submit(() -> {
                try {
                    for (int j = 0; j < 100000; j++) {
                        String one = sequenceStringGenerator1.nextId();
                        // 下面这玩意巨慢 - String 比 Long 慢的多， nextId生成的效率差不多
//                        if (stringConcurrentHashMap.contains(one)) {
//                            throw new RuntimeException("Repeat!" + one);
//                        }

//                        stringConcurrentHashMap.put(one, Thread.currentThread().getName());
                        // faster than number situation
                        if (stringConcurrentHashMap.putIfAbsent(one, new AtomicLong()) != null) {
                            throw new RuntimeException("Repeat!" + one);
                        }
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        long delta = System.nanoTime() - s;
        System.out.println(String.format("Size:%d, TotalConsumeMilliSeconds:%.4f", stringConcurrentHashMap.keySet().size(), (delta / 1000000.0)));

        stringConcurrentHashMap.keySet().stream().sorted().limit(100).forEach(System.out::println);
    }

    private long oneSecond = 1000000000L * 1;

    @Test
    public void oneSecondCount() {
        SequenceStringGenerator sequenceStringGenerator = new SequenceStringGenerator();
        SequenceGenerator sequenceGenerator = new SequenceGenerator();

        long s = System.nanoTime();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        int count = 0;
        while (System.nanoTime() - s < oneSecond) {
            sequenceGenerator.nextId();  // 10/12
            count++;
        }

        int count2 = 0;
        s = System.nanoTime();
        while (System.nanoTime() - s < oneSecond) {
            sequenceStringGenerator.nextId(); // 10/18 的配置，速度是上面的两倍。。
            count2++;
        }

        long delta = System.nanoTime() - s;

        System.out.println("Delta:" + 0 + ", Count:" + count + " - " + count2);
    }

    @Test
    public void one() {
        SequenceStringGenerator sequenceStringGenerator = new SequenceStringGenerator();
        String id = sequenceStringGenerator.nextId();
        System.out.println(id);
        System.out.println(sequenceStringGenerator.getTimestampString(id));
    }
}
