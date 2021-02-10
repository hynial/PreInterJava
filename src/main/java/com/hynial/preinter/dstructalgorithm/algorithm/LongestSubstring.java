package com.hynial.preinter.dstructalgorithm.algorithm;

import java.util.*;

public class LongestSubstring {

    public String[] subStringArr;
    public int longestIdx;

    public int lengthOfLongestSubstring(String s) {

        if (s == null || "".equals(s)) return 0;
        if (s.length() == 1) return 1;

        Vector vector = new Vector();
        this.subStringArr = new String[s.length()];

        int[] subLength = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                char c = s.charAt(j);
                if (!vector.contains(c)) {
                    vector.add(c);
                } else {
                    break;
                }
            }
            subLength[i] = vector.size();
            this.subStringArr[i] = vector.toString();
            vector.clear();
        }

        int longest = 1;
        for (int i = 0; i < subLength.length; i++) {
            if (subLength[i] > longest) {
                longest = subLength[i];
                this.longestIdx = i;
            }
        }

        return longest;
    }

    public int lengthOfLongestSubstringSlidingWindow(String s) {
        Set<Character> set = new HashSet<>();
        int i = 0, j = 0, n = s.length();
        int ans = 0;
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }

        return ans;
    }

    public int lengthOfLongestSubstringSlidingWindowOptimized(String s) {
        Map<Character, Integer> charIdx = new HashMap<>();
        int ans = 0;
        String longestSub = "";
        for (int i = 0, j = 0; j < s.length(); j++) {
            if (charIdx.containsKey(s.charAt(j))) {
                i = Math.max(i, charIdx.get(s.charAt(j)));
            }
            if (ans < j - i + 1) {
                longestSub = s.substring(i, j + 1);
                ans = j - i + 1;
            }
            charIdx.put(s.charAt(j), j + 1);
        }

        return ans;
    }

    public static void main(String[] args) {
        String testStr = "niasfdsfjksdlafjioddsajdkfsjfld";

        LongestSubstring longestSubstring = new LongestSubstring();

        int result = longestSubstring.lengthOfLongestSubstring(testStr);
        System.out.println(result);
        System.out.println(longestSubstring.lengthOfLongestSubstringSlidingWindow(testStr));
        System.out.println(longestSubstring.lengthOfLongestSubstringSlidingWindowOptimized(testStr));

        System.out.println("From idx:" + longestSubstring.longestIdx);
        System.out.println(longestSubstring.subStringArr[longestSubstring.longestIdx]);


        System.out.println("".length());
    }
}