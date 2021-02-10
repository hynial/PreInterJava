package com.hynial.preinter.dstructalgorithm.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LongestPalindrome {

    // 找到回文数
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    // 找到最大环
    public String longestCircle(String s) {

        Map<Character, Integer> idxMap = new HashMap<>();

        int ans = 0;
        String longestSub = "";
        for (int i = 0, j = 0; j < s.length(); j++) {
            if (idxMap.containsKey(s.charAt(j))) {
                idxMap.put(s.charAt(j), Math.min(j, idxMap.get(s.charAt(j))));

                if (idxMap.get(s.charAt(j)) == 0 && j == s.length() - 1) {
                } else {
                    if (ans < j - idxMap.get(s.charAt(j)) + 1) {
                        ans = j - idxMap.get(s.charAt(j)) + 1;
                        longestSub = s.substring(idxMap.get(s.charAt(j)), j + 1);
                    }
                }
            } else {
                if (ans == 0) {
                    ans = 1;
                    longestSub = s.charAt(j) + "";
                }
                idxMap.put(s.charAt(j), j);
            }

        }

        return longestSub;
    }

    public static void main(String[] args) {
        String s = "abcbabc";

        LongestPalindrome longestPalindrome = new LongestPalindrome();

        System.out.println(longestPalindrome.longestPalindrome(s));
        System.out.println(longestPalindrome.longestCircle(s));
    }
}
