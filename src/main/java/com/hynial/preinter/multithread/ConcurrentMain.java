package com.hynial.preinter.multithread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMain {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentMain concurrentMain = new ConcurrentMain();
//        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> sumList = concurrentMain.parallelSum100(map, 100);

        long r = sumList
                .stream()
                .distinct()
                .count();
        if (r != 1) {
            System.out.println("Pretty");
        }

        long wrongResultCount = sumList
                .stream()
                .filter(num -> num != 100)
                .count();

        if (wrongResultCount > 0) {
            System.out.println("Good");
        }
    }

    private List<Integer> parallelSum100(Map<String, Integer> map, int executionTimes) throws InterruptedException {
        List<Integer> sumList = new ArrayList<>(1000);
        for (int i = 0; i < executionTimes; i++) {
            map.put("test", 0);
            ExecutorService executorService =
                    Executors.newFixedThreadPool(4);
            for (int j = 0; j < 10; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < 10; k++)
                        map.computeIfPresent(
                                "test",
                                (key, value) -> value + 1
                        );
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            sumList.add(map.get("test"));
        }

        return sumList;
    }
}
