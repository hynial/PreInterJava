package com.hynial.preinter;

import org.junit.jupiter.api.Test;

public class StringsTest {
    Strings strings = new Strings();
    String string = "aaabb";

    @Test
    public void testLongestSubstring(){
        System.out.println(string);
        System.out.println(strings.longestSubstring(string));
    }
}
