package com.hynial.preinter.bitwise;

import java.util.stream.IntStream;

public class BitOperation {

    public static void main(String[] args) {

        IntStream.rangeClosed(0, 10).forEach(i -> {
            int r = BitUtil.expandSizeByIndex(i);
            System.out.printf("%d --> %d \n", i, r);
        });

        IntStream.rangeClosed(-10, 10).forEach(i -> {
            int r = i >>> 2;
            System.out.printf("%d >>> %d >> %d \n", i, r, i >> 2);
        });

        System.out.printf("%.0f  %d\n", Math.pow(2, 30) - 1, 1 << 30);

        IntStream.rangeClosed(-10, 10).boxed().forEach(i -> {
            String r = Integer.toBinaryString(i);
            System.out.printf("%d --> %s --> length:%d\n", i, r, r.length());
        });
    }

}
