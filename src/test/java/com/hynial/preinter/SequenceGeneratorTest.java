package com.hynial.preinter;

import com.hynial.preinter.generator.SequenceGenerator;
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
        for(int i = 0 ; i < 10; i++){
            SequenceGenerator sequenceGenerator1 = new SequenceGenerator(i);
            int f = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        int j = 100000;
                        while (j > 0) {
                            long id = sequenceGenerator1.nextId();
                            if (concurrentHashMap.containsKey(id)) {
                                throw new IllegalStateException("repeat!");
                            }
                            concurrentHashMap.put(id, Thread.currentThread().getName());
                            j--;
                        }
                    } catch (Throwable throwable){
                        System.out.println(throwable.toString());
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
        System.out.println(delta/1000000.0);
        System.out.println("Size:" + concurrentHashMap.keySet().size());
    }

    @Test
    public void atomic(){
        AtomicLong atomicLong = new AtomicLong();
        int c = 100000;
        long start = System.nanoTime();
        for(int i = 0; i < c; i++){
            atomicLong.incrementAndGet();
        }

        long delta = System.nanoTime() - start;
        System.out.println(delta/1000000.0);
    }
}
