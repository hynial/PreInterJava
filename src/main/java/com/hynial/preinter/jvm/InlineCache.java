package com.hynial.preinter.jvm;

import com.hynial.preinter.jvm.jit.ChinesePassenger;
import com.hynial.preinter.jvm.jit.ForeignerPassenger;
import com.hynial.preinter.jvm.jit.Passenger;

public class InlineCache {
    // Run with: java -XX:CompileCommand='dontinline,*.passThroughImmigration' InlineCache
    public static void main(String[] args) {
        Passenger a = new ChinesePassenger();
        Passenger b = new ForeignerPassenger();
        long current = System.currentTimeMillis();
        long start = current;
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            Passenger c = (i < 1_000_000_000) ? a : b;
            c.passThroughImmigration();
        }
        long end = System.currentTimeMillis();
        System.out.println("Totally:");
        System.out.println(end - start);
    }
}
