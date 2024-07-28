package com.hynial.preinter;

import java.util.*;
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
        boolean optimal = true;
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);

            if (recordIndexMap.get("" + currentChar) != null) {
                int currentLongest = recordIndexMap.size();
                if (currentLongest > tempLongestInt) {
                    tempLongestInt = currentLongest;
                    tempLongest = recordIndexMap.keySet().stream().collect(Collectors.joining(""));
                }

                i = recordIndexMap.get("" + currentChar); // TODO 此步骤可优化，重复的起始位置到当前位置可以不用再走一遍
                if (!optimal) {
                    recordIndexMap.clear();
                } else {
                    int finalI = i;
                    recordIndexMap = recordIndexMap.entrySet().stream().filter(entry -> entry.getValue() > finalI).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue(), (x, y) -> x, LinkedHashMap::new));
                    i = i + recordIndexMap.size();
                }
            } else {
                recordIndexMap.put("" + currentChar, i);
                if (i == s.length() - 1) {
                    int currentLongest = recordIndexMap.size();
                    if (currentLongest > tempLongestInt) {
                        tempLongestInt = currentLongest;
                        tempLongest = recordIndexMap.keySet().stream().collect(Collectors.joining(""));
                    }
                }
            }
        }

        return tempLongest;
    }

    /**
     * 判断一个字符串是否满足只包含(){}[]，且成对出现，顺序正确
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        // 奇偶判断
        if (s == null || s.length() % 2 == 1) {
            return false;
        }

        HashMap<String, String> rightKeyBraceMap = new HashMap<>();
        rightKeyBraceMap.put(")", "(");
        rightKeyBraceMap.put("]", "[");
        rightKeyBraceMap.put("}", "{");

        boolean optimal = true;
        for (; s.length() > 0; ) {
            int len = s.length();
            if (!s.contains(")") && !s.contains("]") && !s.contains("}")) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                String braceMayRight = String.valueOf(s.charAt(i));
                if (rightKeyBraceMap.keySet().contains(braceMayRight)) {
                    if (i - 1 < 0) {
                        return false;
                    }
                    String frontChar = String.valueOf(s.charAt(i - 1));
                    if (frontChar.equals(rightKeyBraceMap.get(braceMayRight))) {
                        // 优化处：i 可以往后移，避免重复走前路
                        if (!optimal) {
                            if (i - 2 < 0) {
                                s = i + 1 <= len - 1 ? s.substring(i + 1) : "";
                            } else {
                                s = s.substring(0, i - 1) + (i + 1 <= len - 1 ? s.substring(i + 1) : "");
                            }
                            break;
                        } else {

                            // 优化 代码
                            String front = "";
                            String after = "";
                            int t = i;
                            if (i + 1 <= len - 1) {
                                after = s.substring(i + 1);
                                t = i + 1 - 2 - 1;
                            }

                            if (i - 2 >= 0) {
                                front = s.substring(0, i - 1);
                            }

                            s = front + after;
                            len = s.length();
                            i = t;
                        }
                    } else {
                        return false;
                    }
                }
            }

        }

        return true;
    }

    // 采用数据结构 去包装 省去了一层循环，不过上面的方法更细腻，这个方法比较简单
    public boolean isValid2(String s) {
        if (s == null || s.length() % 2 == 1) {
            return false;
        }

        HashMap<String, String> rightKeyBraceMap = new HashMap<>();
        rightKeyBraceMap.put(")", "(");
        rightKeyBraceMap.put("]", "[");
        rightKeyBraceMap.put("}", "{");

        LinkedList<String> stack = new LinkedList<>();

        for (int i = 0; i < s.length(); i++) {
            String ch = String.valueOf(s.charAt(i));
            boolean leftFlag = rightKeyBraceMap.values().contains(ch);
            if (leftFlag) {
                stack.push(ch);
            } else {
                if (!stack.isEmpty()) {
                    if (!rightKeyBraceMap.get(ch).equals(stack.pop())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    // 成对替换
    public boolean isValid3(String s) {
        if (s == null || s.length() % 2 == 1) {
            return false;
        }
        Set<String> bracePairs = new HashSet<>();
        bracePairs.add("()");
        bracePairs.add("[]");
        bracePairs.add("{}");

        while (true) {
//            boolean end = true;
//            if (s.indexOf("()") > -1) {
//                s = s.replaceAll("\\(\\)", "");
//                end = false;
//            }
//
//            if (s.indexOf("[]") > -1) {
//                s = s.replaceAll("\\[\\]", "");
//                end = false;
//            }
//
//            if (s.indexOf("{}") > -1) {
//                s = s.replaceAll("\\{\\}", "");
//                end = false;
//            }
//            if (end) {
//                return s.length() == 0;
//            }
            String s2 = s.replaceAll("(\\(\\)|\\[\\]|\\{\\})", "");
            if (s2.length() == s.length()) {
                return s2.length() == 0;
            }
            s = s2;
        }
    }

    // 递归
    public boolean isValid4(String s) {
        if (s == null || s.length() % 2 == 1) {
            return false;
        }

        String s2 = s.replaceAll("(\\(\\)|\\[\\]|\\{\\})", "");
        if (s2.length() == s.length()) {
            return s2.length() == 0;
        }

        return isValid4(s2);
    }

    // 两个两个找 - 分错开情形
    public boolean isValid5(String s) {
        if (s == null || s.length() % 2 == 1) {
            return false;
        }

        Set<String> bracePairs = new HashSet<>();
        bracePairs.add("()");
        bracePairs.add("[]");
        bracePairs.add("{}");

        StringBuilder tmp = new StringBuilder(s);

        while(true) {
            StringBuilder tmp2 = new StringBuilder();
            for (int i = 0; i < tmp.length(); i += 2) {
                String pair = tmp.substring(i, i + 2);
                if (!bracePairs.contains(pair)) {
                    tmp2.append(pair);
                }
            }
            StringBuilder tmp3 = new StringBuilder();
            for (int i = 1; i < tmp2.length(); i += 2) {
                String pair = tmp2.substring(i, i + 2 <= tmp2.length() ? i + 2 : i + 1);
                if (!bracePairs.contains(pair)) {
                    tmp3.append(pair);
                }
            }
            if (tmp2.length() > 0) {
                tmp3.insert(0, tmp2.charAt(0));
            }

            if (tmp.length() == tmp3.length()) {
                return tmp3.length() == 0;
            }

            tmp = tmp3;
        }
    }
}
