package com.hynial.preinter.cpuprocess.performancetesting;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @https://github.com/holograph/examples/blob/master/how-shit-works-cpu/src/main/java/com/tomergabel/examples/cpu/BranchingShowcase.java
 * JMH 性能测试框架 Java Microbenchmark Harness
 * https://www.xncoding.com/2018/01/07/java/jmh.html
 * https://dafengge0913.github.io/jmh/
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Thread)
public class BranchingShowcase {
    private final static int DATA_SIZE = 32 * 1024 * 1024;
    private final static long RANDOM_SEED = 0xdeadbeef;

    private byte[] data;
    private byte[] presorted;

    @Setup
    public void setup() {
        data = new byte[DATA_SIZE];
        new Random(RANDOM_SEED).nextBytes(data);
        presorted = data.clone();
        Arrays.sort(presorted);
    }

    @Benchmark
    public long baseline() {
        long sum = 0;
        for (int c = 0; c < DATA_SIZE; c++)
            if (data[c] >= 0)
                sum += data[c];
        return sum;
    }

    @Benchmark
    public long presorted() {
        long sum = 0;
        for (int c = 0; c < DATA_SIZE; c++)
            if (presorted[c] >= 0)
                sum += presorted[c];
        return sum;
    }

    @Benchmark
    public long predicated() {
        long sum = 0;
        for (int c = 0; c < DATA_SIZE; c++)
            if (data[c] >= 0)
                sum += data[c];
        return sum;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BranchingShowcase.class.getSimpleName())
                .output("./Benchmark.log")
                .build();
        new Runner(options).run();

//        Options opt = new OptionsBuilder()
//                .include(SecondBenchmark.class.getSimpleName())
//                .forks(1)
//                .warmupIterations(5)
//                .measurementIterations(2)
//                .build();
//        Collection<RunResult> results =  new Runner(opt).run();
//        ResultExporter.exportResult("单线程与多线程求和性能", results, "length", "微秒");
    }
}