package com.hynial.preinter.dstructalgorithm.algorithm.find.indexofmatcher;

public class LastByteSuffixMatcher {

    private final byte [] pattern;
    private final int [] shifts;
    private final int [] suffix_shifts; // index is the suffix length

    public LastByteSuffixMatcher(byte[] pattern) {
        this.pattern = pattern;
        int len = pattern.length;
        shifts = new int [256];
        for (int i = 0; i < 256; i++) {
            shifts [i] = len;
        }
        for (int i = 0; i < len-1; i++) {
            shifts [pattern[i] & 0xFF] = len - i - 1;
        }

        suffix_shifts = new int [pattern.length];
        suffix_shifts [0] = shifts [pattern [len-1] & 0xFF];
        int atzero_len = 0;

        for (int suffix_len = 1; suffix_len < pattern.length; suffix_len ++) {
            int pos = find (pattern, suffix_len);
            int suffix_shift;

            if (pos < 0) {
                suffix_shift = len - atzero_len;
            } else {
                suffix_shift = len - pos - suffix_len;
            }
            suffix_shifts [suffix_len] = suffix_shift;

            if (compare (pattern, len - suffix_len, pattern, 0, suffix_len)) {
                atzero_len = suffix_len;
            }
        }
    }

    private int find (byte [] pattern, int suffix_len)
    {
        int len = pattern.length;

        for (int pos = len - suffix_len - 1; pos >= 0; pos --) {
            if (compare (pattern, pos, pattern, len - suffix_len, suffix_len)) {
                return pos;
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

    public int indexOf (byte[] text, int fromIndex)
    {
        byte [] pattern = this.pattern;
        int len = pattern.length;
        byte last = pattern [len-1];
        int [] shifts = this.shifts;

        for (int pos = fromIndex; pos <= text.length - len;) {
            byte b = text [pos + len - 1];
            int shift;
            if (b != last) {
                shift = shifts [b & 0xFF];
            } else {
                int i = len-2;
                while (true) {
                    if (text [pos + i] != pattern [i]) {
                        break;
                    }
                    if (i == 0) {
                        return pos;
                    }
                    -- i;
                }
                int suffix_len = len - i - 1;
                shift = suffix_shifts [suffix_len];
            }
            pos += shift;
        }
        return -1;
    }

    public static void main(String[] args) {
        LastByteSuffixMatcher lastByteSuffixMatcher = new LastByteSuffixMatcher("c1".getBytes());
        int i = lastByteSuffixMatcher.indexOf("abc123".getBytes(), 0);
        System.out.println(i);
    }
}
