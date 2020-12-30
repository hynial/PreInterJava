package com.hynial.preinter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Strings {
    /**
     * 给定一个字符串，找出不含有重复字符的最长子串
     *
     * @param s
     * @return
     */
    public String longestSubstring(String s) {
        Map<String, Integer> recordIndexMap = new LinkedHashMap<>();

        int tempLongestInt = 0;
        String tempLongest = "";
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);

            if (recordIndexMap.get("" + currentChar) != null) {
                int currentLongest = recordIndexMap.size();
                if (currentLongest > tempLongestInt) {
                    tempLongestInt = currentLongest;
                    tempLongest = recordIndexMap.keySet().stream().collect(Collectors.joining(""));
                }

                i = recordIndexMap.get("" + currentChar); // TODO 此步骤可优化，重复的起始位置到当前位置可以不用再走一遍
                recordIndexMap.clear();
            } else {
                recordIndexMap.put("" + currentChar, i);
            }

            if (i == s.length() - 1) {
                int currentLongest = recordIndexMap.size();
                if (currentLongest > tempLongestInt) {
                    tempLongestInt = currentLongest;
                    tempLongest = recordIndexMap.keySet().stream().collect(Collectors.joining(""));
                }
            }
        }

        return tempLongest;
    }
}
