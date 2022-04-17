package com.hynial.preinter.dstructalgorithm.algorithm.find.indexofmatcher;

/**
 * https://pzemtsov.github.io/2019/04/13/searching-for-better-indexof.html
 */
public class IndexOfByLastMatcher {
    private final byte [] pattern;
    private final int [] shifts;

    public IndexOfByLastMatcher(byte[] pattern) {
        this.pattern = pattern;

        int len = pattern.length;
        shifts = new int [256];
        // 初始化移动位数 - 假设都不在模式串中，一次可以直接移动模式串长度
        for (int i = 0; i < 256; i++) {
            shifts [i] = len;
        }

        for (int i = 0; i < len-1; i++) {
            // 倘若在模式串中，一次可以移动的位数为总长度减去其在模式串中的下标位置
            shifts [pattern[i] & 0xFF] = len - i - 1;
        }
    }

    public int indexOf (byte[] text, int fromIndex) {
        byte [] pattern = this.pattern;
        int pattern_len = pattern.length;
        byte last = pattern [pattern_len-1];
        int [] shifts = this.shifts;

        for (int pos = fromIndex; pos < text.length - pattern_len + 1;) {
            byte b = text [pos + pattern_len - 1];
            if (b == last) {
                if (compare (text, pos, pattern, pattern_len-1)) {
                    return pos;
                }
            }
            int shift = shifts [b & 0xFF];
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
        String text = "abc123";
        String pattern = "c1";

        IndexOfByLastMatcher indexOfByLastMatcher = new IndexOfByLastMatcher(pattern.getBytes());
        int index = indexOfByLastMatcher.indexOf(text.getBytes(), 0);
        System.out.println(index);

        String hanzi = "汉字";
        String hz = "hz";
        System.out.println(hanzi + ":" + hanzi.length());
        System.out.println(hanzi.getBytes().length);
        System.out.println(hz.length());
        System.out.println(hz.getBytes().length);

        System.out.println(8%3);
        System.out.println(8 & 16);
    }
}
