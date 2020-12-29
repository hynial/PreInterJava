package com.hynial.preinter.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationUtil {
    public static void printArray(int[] intArr) {
        System.out.println(Arrays.stream(intArr).boxed().map(Object::toString).collect(Collectors.joining(", ")));
        System.out.println();
    }

    public static void printListOfList(List<List<Integer>> lists){
        lists.stream().forEach(list -> System.out.println(list.stream().map(Object::toString).collect(Collectors.joining(", "))));
    }
}
