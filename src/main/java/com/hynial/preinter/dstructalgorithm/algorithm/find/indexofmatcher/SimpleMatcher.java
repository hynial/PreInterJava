package com.hynial.preinter.dstructalgorithm.algorithm.find.indexofmatcher;

public class SimpleMatcher {
    private final byte [] pattern;

    public SimpleMatcher(byte[] pattern) {
        this.pattern = pattern;
    }

    public int indexOf (byte[] text, int fromIdx) {
        int pattern_len = pattern.length;
        int text_len = text.length;
        for (int i = fromIdx; i + pattern_len <= text_len; i++) {
            if (compare (text, i, pattern, pattern_len)) return i;
        }
        return -1;
    }

    public static boolean compare (byte [] text, int offset,
                                   byte [] pattern, int pattern_len)
    {
        for (int i = 0; i < pattern_len; i++) {
            if (text [offset + i] != pattern [i]) return false;
        }
        return true;
    }
}
