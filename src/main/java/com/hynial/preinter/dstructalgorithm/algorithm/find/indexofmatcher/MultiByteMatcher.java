package com.hynial.preinter.dstructalgorithm.algorithm.find.indexofmatcher;

public class MultiByteMatcher {
    private final byte [] pattern;
    private final int [][] shifts;

    public MultiByteMatcher(byte[] pattern) {
        this.pattern = pattern;

        int len = pattern.length;
        shifts = new int [pattern.length][256];
        for (int pos = len-1; pos >= 0; pos --) {
            for (int i = 0; i < 256; i++) {
                shifts [pos][i] = pos+1;
            }
            for (int i = 0; i < pos; i++) {
                shifts [pos][pattern[i] & 0xFF] = pos - i;
            }
        }
    }

    public int indexOf (byte[] text, int fromIndex) {
        byte [] pattern = this.pattern;
        int pattern_len = pattern.length;
        int [][] shifts = this.shifts;

        for (int pos = fromIndex; pos < text.length - pattern_len + 1;) {
            if (compare (text, pos, pattern, pattern_len)) {
                return pos;
            }

            int shift = 0;
            for (int i = pattern_len - 1; i >= 0; i--) {
                int sh = shifts [i] [text [pos + i] & 0xff];
                if (sh > shift) shift = sh;
                if (shift > i) {
                    break;
                }
            }
            pos += shift;
        }
        return -1;
    }

    private boolean compare(byte[] text, int pos, byte[] pattern, int endPos){
        for(int i = 0; i < endPos; i++){
            if(text[i + pos] != pattern[i]){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        MultiByteMatcher multiByteMatcher = new MultiByteMatcher("c1".getBytes());

        int i = multiByteMatcher.indexOf("abc123".getBytes(), 0);
        System.out.println(i);
    }
}
