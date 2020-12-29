package com.hynial.preinter;

import com.hynial.preinter.util.ApplicationUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class FindsTest {
    private int intArr[] = IntStream.of(8, 2, 5, 4, 3, 9, 7, 2, 5).toArray();
    private int intArr2[] = IntStream.of(1, 2, 3).toArray();

    private Finds finds = new Finds();

    @Test
    public void testFindMaxRight(){
        int[] testRights = intArr.clone();
        ApplicationUtil.printArray(testRights);
        testRights = finds.findMaxRight(testRights);
        ApplicationUtil.printArray(testRights);
    }

    @Test
    public void testNextPermutation(){
        int[] testNext = intArr2.clone();
        ApplicationUtil.printArray(testNext);
        finds.nextPermutation(testNext);
        ApplicationUtil.printArray(testNext);
    }

    @Test
    public void testPermute(){
        int[] perm = intArr2.clone();
        ApplicationUtil.printArray(perm);
        System.out.println();
        List<List<Integer>> perms = finds.permute(perm);
        ApplicationUtil.printListOfList(perms);
    }
}
