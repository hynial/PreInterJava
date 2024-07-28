package com.hynial.preinter;

import org.junit.jupiter.api.Test;

import java.util.List;

public class StringsTest {
    Strings strings = new Strings();
    String string = "aaabcd";

    @Test
    public void testLongestSubstring(){
        System.out.println(string);
        System.out.println(strings.longestSubstring(string));
    }

    @Test void testValidBracePair() {
        String s = "(({})[()[]]){()}";
        String s1 = "(({})[()[]]){(]}";
        String s2 = "(((((((";
        String s3 = "(((((((]";
        String s4 = "((((((()";
        String s5 = "))))))";
        String s6 = "))))))(";
        String s7 = "())))))";
        String s8 = "()))))){}";
        String s9 = "(((((()))))){}";

        List<String> testStrings = List.of(s, s1, s2, s3, s4, s5, s6, s7, s8, s9);

//        System.out.println(strings.isValid(s));
//        System.out.println(strings.isValid2(s));
//        System.out.println(strings.isValid3(s));
//        System.out.println(strings.isValid4(s));

        testStrings.forEach(ts -> {
            System.out.println(strings.isValid(ts) + "\t" + strings.isValid2(ts) + "\t" + strings.isValid4(ts) + "\t" + strings.isValid5(ts));
        });
    }
}
