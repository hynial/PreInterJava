package com.hynial.preinter.bitwise;

public class BitUtil {

    public static int expandSizeByIndex(int index) {
        int newCapacity = index;
        newCapacity |= newCapacity >>>  1;
        newCapacity |= newCapacity >>>  2;
        newCapacity |= newCapacity >>>  4;
        newCapacity |= newCapacity >>>  8;
        newCapacity |= newCapacity >>> 16;
        newCapacity ++;
        return newCapacity;
    }
}
