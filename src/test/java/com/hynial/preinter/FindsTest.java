package com.hynial.preinter;

import com.hynial.preinter.util.ApplicationUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class FindsTest {
    private int intArr[] = IntStream.of(8, 2, 5, 4, 3, 9, 7, 2, 5).toArray();

    private Finds finds = new Finds();

    @Test
    public void testFindMaxRight(){
        int[] testRights = intArr.clone();
        testRights = finds.findMaxRight(testRights);
        ApplicationUtil.printArray(testRights);
    }
}
