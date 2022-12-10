package com.hynial.preinter.sorts;

import com.hynial.preinter.Sorts;
import com.hynial.preinter.util.ApplicationUtil;
import com.hynial.tool.gen.Rand;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Thread)
public class BenchSortTest {
    private static final int PAYLOAD_LEN = 10;
    private static final int ARRAY_LEN = 10000;
    private int[][] payloads = new int[PAYLOAD_LEN][ARRAY_LEN];
    private int[][] payloads2 = new int[PAYLOAD_LEN][ARRAY_LEN];

    private Sorts sorts = new Sorts();

    @Setup
    public void setup() {
        for (int i = 0; i < PAYLOAD_LEN; i++) {
            payloads[i] = new Rand.Pint().array(ARRAY_LEN, i);
            payloads2[i] = Arrays.copyOf(payloads[i], ARRAY_LEN);
        }
    }

    @Benchmark
    public void quickSort1() {
        IntStream.range(0, PAYLOAD_LEN).forEach(i -> {
            sorts.quickSort(payloads[i], 0, ARRAY_LEN - 1);
//            sorts.bubbleSort(payloads[i], ARRAY_LEN);
        });
    }

    @Benchmark
    public void quickSort2() {
        IntStream.range(0, PAYLOAD_LEN).forEach(i -> {
             sorts.quickSort2(payloads2[i], 0, ARRAY_LEN - 1);
//            sorts.bubbleSort(payloads2[i], ARRAY_LEN);
        });
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchSortTest.class.getSimpleName())
                .output("./Sorts_Benchmark.log")
                .build();
        new Runner(options).run();
    }
}
