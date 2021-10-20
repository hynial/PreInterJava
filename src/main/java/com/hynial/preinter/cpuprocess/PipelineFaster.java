package com.hynial.preinter.cpuprocess;

import java.util.Arrays;
import java.util.Random;

/**
 * cpu ：fetch - decode - execute - memory access - write-back
 */
public class PipelineFaster {
    public static void main(String[] args) {
        byte[] data = new byte[32768];
        new Random().nextBytes(data);

//        System.out.println(Arrays.toString(data));
//        Arrays.sort(data); // order of magnitude - order makes it faster for condition judge : data[i] > 0

        long start = System.nanoTime();
        long sum = 0;
        for(int i = 0; i < data.length; i++){
            if(data[i] > 0){
                sum += data[i];
            }
        }
        long end = System.nanoTime();
        System.out.println("Result:" + sum + ", Consume:" + (end - start));

//        System.out.println(Arrays.toString(data));
    }
}
