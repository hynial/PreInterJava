package com.hynial.preinter;

import com.hynial.preinter.util.ApplicationUtil;
import com.hynial.tool.gen.Rand;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SortsTest {
    Sorts sorts = new Sorts();
    private int[] intArr = IntStream.of(8, 2, 5, 4, 3, 9, 7, 2, 5).toArray();

    @Test
    public void testChooseSortUpgrade(){
        int[] testSimple = intArr.clone();
        ApplicationUtil.printArray(testSimple);
        sorts.chooseSortUpgrade(testSimple, testSimple.length);
        ApplicationUtil.printArray(testSimple);
    }

    @Test
    public void testBubbleSort(){
        int[] testBubble = intArr.clone();
        ApplicationUtil.printArray(testBubble);
        sorts.bubbleSort(testBubble, testBubble.length);
        ApplicationUtil.printArray(testBubble);
    }

    @Test
    public void testMergeSort(){
        int[] testMerge = intArr.clone();
        ApplicationUtil.printArray(testMerge);
        sorts.mergeSort(testMerge);
        ApplicationUtil.printArray(testMerge);
    }

    @Test
    public void testHeapSort(){
        int[] testHeap = intArr.clone();
        ApplicationUtil.printArray(testHeap);
        Sorts.HeapSort.sort(testHeap);
        System.out.println(Arrays.toString(testHeap));
    }

    @Test
    public void testQuickSort2() {
        IntStream.range(0, 5).forEach(i -> {
            int[] arr = new Rand.Pint(i).array(20, -1000);
            System.out.println(Arrays.toString(arr));
            sorts.quickSort2(arr, 0, arr.length - 1);
            System.out.println(Arrays.toString(arr));
            System.out.println();
        });
    }
}
