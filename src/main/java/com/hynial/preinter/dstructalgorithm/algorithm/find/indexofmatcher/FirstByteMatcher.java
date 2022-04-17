package com.hynial.preinter.dstructalgorithm.algorithm.find.indexofmatcher;

public class FirstByteMatcher {
    private final byte [] pattern;

    public FirstByteMatcher(byte[] pattern) {
        this.pattern = pattern;
    }

    public int indexOf (byte[] text, int fromIdx) {
        int text_len = text.length;
        int pattern_len =  pattern.length;
        for (int i = fromIdx; i <= text_len - pattern_len; i++) {
            if (text [i] == this.pattern[0]) {
                if (compare (text, i + 1, pattern, 1, pattern_len - 1)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean compare (byte [] text, int offset,
                                   byte [] pattern, int p_offset, int pattern_len) {
        for (int i = 0; i < pattern_len; i++) {
            if (text [offset + i] != pattern [i + p_offset]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        FirstByteMatcher firstByteMatcher = new FirstByteMatcher("c1234".getBytes());

        int i = firstByteMatcher.indexOf("9090abc123".getBytes(), 0);
        System.out.println(i);
    }
}
